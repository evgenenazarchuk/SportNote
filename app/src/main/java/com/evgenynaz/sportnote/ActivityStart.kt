package com.evgenynaz.sportnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.evgenynaz.sportnote.databinding.ActivityStartBinding
import com.evgenynaz.sportnote.weather.WeatherViewModel
import com.evgenynaz.sportnote.weather.data.WeatherResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityStart: AppCompatActivity()  {
    private lateinit var binding: ActivityStartBinding
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.liveData.observe(this, {
            // Погода
            update(it)
        })
        binding.lWeather.setOnClickListener{
            viewModel.getResultWeather()
        }

    }


    private fun update(resultWeather: WeatherResult) {

        binding.tvCity.text = resultWeather?.name
        binding.tvTemp.text = "${resultWeather?.temp?.toInt()} °C"
    //    binding.tvCloud.text = resultWeather?.description
            ?.replace("[", "")
            ?.replace("]", "")
        val url = "https://openweathermap.org/img/wn/${resultWeather?.iconId}@2x.png"
            .replace("[", "")
            .replace("]", "")

        Glide
            .with(binding.root)
            .load(url)
            .placeholder(R.drawable.img)
            .into(binding.iconWeather)
    }
}


