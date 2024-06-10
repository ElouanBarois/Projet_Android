package fr.epf.min1.flags

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.epf.min1.flags.R
import kotlin.math.log

class CountryDetails : AppCompatActivity() {
    private var isLiked = false
    private lateinit var country: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_details)

        val countryName = intent.getStringExtra("countryName") ?: ""
        val capital = intent.getStringExtra("capital") ?: ""
        val population = intent.getIntExtra("population", 0)
        val flagUrl = intent.getStringExtra("flagUrl") ?: ""
        println(flagUrl)

        country = Country(Name(countryName, ""), Flags(flagUrl, flagUrl), listOf(capital), population, "", "")

        val textViewCountryName = findViewById<TextView>(R.id.textViewCountryName)
        textViewCountryName.text = countryName

        val textViewCapital = findViewById<TextView>(R.id.textViewCapital)
        textViewCapital.text = "Capitale: $capital"

        val textViewPopulation = findViewById<TextView>(R.id.textViewPopulation)
        textViewPopulation.text = "Population: $population"

        val imageViewFlag = findViewById<ImageView>(R.id.imageViewFlag)
        if (flagUrl.isNotEmpty()) {
            Glide.with(this)
                .load(flagUrl)
                .into(imageViewFlag)
        } else {
            Log.e(TAG, "FlagUrl est null/empty")
        }

        val imageViewHeart: ImageView = findViewById(R.id.imageViewHeart)
        imageViewHeart.setOnClickListener {
            isLiked = !isLiked
            updateHeartIcon(imageViewHeart)
            if (isLiked) {
                saveLikedCountry(Country(Name(countryName, ""), Flags(flagUrl, flagUrl), listOf(capital), population, "", ""))
            } else {
                removeLikedCountry(country.name.common)
                sendUnlikedBroadcast(country.name.common)
            }
        }

        val buttonGoBack = findViewById<Button>(R.id.buttonGoBack)
        buttonGoBack.setOnClickListener {
            finish()
        }
        isLiked = isCountryLiked(countryName)
        updateHeartIcon(imageViewHeart)
    }

    private fun updateHeartIcon(imageViewHeart: ImageView) {
        if (isLiked) {
            imageViewHeart.setImageResource(R.drawable.ic_heart_liked)
        } else {
            imageViewHeart.setImageResource(R.drawable.ic_heart_unliked)
        }
    }

    private fun saveLikedCountry(country: Country) {
        val sharedPreferences = getSharedPreferences("liked_countries", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val countryJson = Gson().toJson(country)
        editor.putString(country.name.common, countryJson)
        editor.apply()
    }

    private fun removeLikedCountry(countryName: String) {
        val sharedPreferences = getSharedPreferences("liked_countries", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(countryName)
        editor.apply()
    }

    private fun sendUnlikedBroadcast(countryName: String) {
        val intent = Intent("fr.epf.min1.flags.UNLIKE_COUNTRY")
        intent.putExtra("countryName", countryName)
        sendBroadcast(intent)
    }
    private fun isCountryLiked(countryName: String): Boolean {
        val sharedPreferences = getSharedPreferences("liked_countries", MODE_PRIVATE)
        return sharedPreferences.contains(countryName)
    }
}
