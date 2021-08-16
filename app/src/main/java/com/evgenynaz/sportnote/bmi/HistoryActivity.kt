package com.evgenynaz.sportnote.bmi

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.evgenynaz.sportnote.databinding.ActivityHistoryBmiBinding
import kotlinx.android.synthetic.main.activity_history_bmi.*

class HistoryActivity:AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar_history_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)//set back button
        }


        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }


    }

}