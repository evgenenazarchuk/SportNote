package com.evgenynaz.sportnote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.evgenynaz.sportnote.alarm.AlarmActivityFinish
import com.evgenynaz.sportnote.bmi.BMIActivity
import com.evgenynaz.sportnote.databinding.ActivityStartBinding
import com.evgenynaz.sportnote.map.MapsActivity
import com.evgenynaz.sportnote.note.ActivityNote
import com.evgenynaz.sportnote.weather.WeatherViewModel
import kotlinx.android.synthetic.main.activity_start.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityStart : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        bmi_ll.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
        note.setOnClickListener {
            val intent1 = Intent(this, ActivityNote::class.java)
            startActivity(intent1)
        }
        maps.setOnClickListener {
            val intent2 = Intent(this, MapsActivity::class.java)
            startActivity(intent2)
        }
        alarm_im.setOnClickListener {
            val intent3 = Intent(this, AlarmActivityFinish::class.java)
            startActivity(intent3)
        }
    }
}


