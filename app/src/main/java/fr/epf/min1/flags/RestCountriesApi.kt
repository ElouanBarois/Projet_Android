package fr.epf.min1.flags

import fr.epf.min1.flags.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountriesApi {
    @GET("v3.1/independent?status=true")
    fun getAllCountries(): Call<List<Country>>

    @GET("v3.1/name/{name}")
    fun searchCountries(@Query("name") name: String): Call<List<Country>>
}