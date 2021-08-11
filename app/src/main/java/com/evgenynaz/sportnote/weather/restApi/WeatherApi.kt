package com.example.schoolorgonizer.weather.restApi

import com.example.schoolorgonizer.weather.entities.WeatherResponse
import com.example.schoolorgonizer.weather.restApi.ApiRepository.Companion.CITY
import com.example.schoolorgonizer.weather.restApi.ApiRepository.Companion.LANG
import com.example.schoolorgonizer.weather.restApi.ApiRepository.Companion.UNITS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?appid=75338e8fd7f3af6af7d6befa30af8edd")          //BuildConfig.API_KEY2
    suspend fun getWeather(
        @Query("q") nameCity: String = CITY,
        @Query("lang") lang: String = LANG,
        @Query("units") units: String = UNITS,
    ): WeatherResponse

    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/"

        fun get(): WeatherApi = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build())
            .build().create(WeatherApi::class.java)
    }
}