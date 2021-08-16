package com.evgenynaz.sportnote.weather.data
data class WeatherResult(
    val description: String,
    val name: String,
    val temp: Double,
    val iconId: String
)