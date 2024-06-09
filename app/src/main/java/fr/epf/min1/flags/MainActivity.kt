package fr.epf.min1.flags

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextSearch: EditText
    private lateinit var countryAdapter: CountryAdapter
    private val countries = mutableListOf<Country>()
    private val allCountries = mutableListOf<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        editTextSearch = findViewById(R.id.editTextSearch)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(countries) { country ->
            val intent = Intent(this, CountryDetails::class.java)
            intent.putExtra("countryName", country.name.common)
            intent.putExtra("capital", country.capital.joinToString(", "))
            intent.putExtra("population", country.population)
            intent.putExtra("flagUrl", country.flags.png)
            startActivity(intent)
        }
        recyclerView.adapter = countryAdapter

        fetchAllCountries()

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                filterCountries(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_draw ->{
                val intent = Intent(this, DrawFlagActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun fetchAllCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = tryFetchCountry(RetrofitClient.instance, "")
            withContext(Dispatchers.Main) {
                allCountries.clear()
                allCountries.addAll(result)
                countries.clear()
                countries.addAll(result)
                countryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun filterCountries(query: String) {
        val filteredList = if (query.isEmpty()) {
            allCountries
        } else {
            allCountries.filter { country ->
                val countryNameMatches = country.name.common.contains(query, ignoreCase = true)
                val capitalMatches = country.capital.any { it.contains(query, ignoreCase = true) }
                countryNameMatches || capitalMatches
            }
        }
        countries.clear()
        countries.addAll(filteredList)
        countryAdapter.notifyDataSetChanged()
    }

    private fun tryFetchCountry(countryService: RestCountriesApi, searchName: String): List<Country> {
        val maxRetries = 15
        var currentRetry = 0
        var success = false
        var countriesList: List<Country> = emptyList()

        while (currentRetry < maxRetries && !success) {
            try {
                val response = if (searchName.isEmpty()) {
                    countryService.getAllCountries().execute()
                } else {
                    countryService.searchCountries(searchName).execute()
                }

                if (response.isSuccessful && response.body() != null) {
                    countriesList = response.body()!!
                    Log.d("MainActivity", "Fetched countries: $countriesList")
                    success = true
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch countries: ${e.message}")
            }
            currentRetry++
        }
        return countriesList
    }
}
