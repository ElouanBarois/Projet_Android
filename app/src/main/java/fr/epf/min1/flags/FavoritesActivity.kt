package fr.epf.min1.flags

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class FavoritesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private val favoriteCountries = mutableListOf<Country>()

    private val unlikedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val countryName = intent?.getStringExtra("countryName")
            if (countryName != null) {
                removeCountryFromFavorites(countryName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(favoriteCountries) { country ->
            val intent = Intent(this, CountryDetails::class.java)
            intent.putExtra("countryName", country.name.common)
            intent.putExtra("capital", country.capital.joinToString(", "))
            intent.putExtra("population", country.population)
            intent.putExtra("flagUrl", country.flags.png)

            startActivity(intent)
        }
        recyclerView.adapter = countryAdapter

        loadFavoriteCountries()
        registerReceiver(unlikedReceiver, IntentFilter("fr.epf.min1.flags.UNLIKE_COUNTRY"),
            RECEIVER_NOT_EXPORTED
        )
        val buttonGoBack = findViewById<Button>(R.id.buttonGoBack)
        buttonGoBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlikedReceiver)
    }

    private fun loadFavoriteCountries() {
        val sharedPreferences = getSharedPreferences("liked_countries", MODE_PRIVATE)
        val allEntries = sharedPreferences.all

        favoriteCountries.clear()
        for (entry in allEntries) {
            val countryJson = entry.value as String
            val country = Gson().fromJson(countryJson, Country::class.java)
            favoriteCountries.add(country)
        }
        countryAdapter.notifyDataSetChanged()
    }

    private fun removeCountryFromFavorites(countryName: String) {
        favoriteCountries.removeAll { it.name.common == countryName }
        countryAdapter.notifyDataSetChanged()
    }


}
