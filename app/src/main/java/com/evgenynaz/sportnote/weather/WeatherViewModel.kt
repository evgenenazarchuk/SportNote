package com.evgenynaz.sportnote.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenynaz.sportnote.weather.data.WeatherResult
import com.evgenynaz.sportnote.weather.restApi.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {

    val liveData: MutableLiveData<WeatherResult> = MutableLiveData()

    init {
        getResultWeather()
    }

     fun getResultWeather() {
        try {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepository.getApiResult()
                }
                liveData.postValue(result)
            }

        } catch (e: Exception) {
            print(e)
        }
    }
}