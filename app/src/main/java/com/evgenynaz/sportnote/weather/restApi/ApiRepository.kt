package com.evgenynaz.sportnote.weather.restApi

import com.evgenynaz.sportnote.weather.data.WeatherResult


class ApiRepository(
    private val weatherApi: WeatherApi
) {
    suspend fun getApiResult(): WeatherResult = WeatherResult(

        weatherApi.getWeather().weather.map {
            it.description
        }.toString(),
        weatherApi.getWeather().name,
        weatherApi.getWeather().main.temp,
        weatherApi.getWeather().weather.map {
            it.icon
        }.toString()
    )

    companion object {
        const val CITY: String = "Минск"
        const val LANG = "ru"
        const val UNITS = "metric"
    }
}