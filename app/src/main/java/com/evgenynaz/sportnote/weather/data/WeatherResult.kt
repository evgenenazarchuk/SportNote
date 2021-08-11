package com.example.schoolorgonizer.weather.data

data class WeatherResult(
    val description: String,
    val name: String,
    val temp: Double,
    val iconId: String
)