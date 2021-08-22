package com.evgenynaz.sportnote.bmi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgenynaz.sportnote.databinding.ActivityHistoryBmiBinding
import com.evgenynaz.sportnote.note.App
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_history_bmi.*


class HistoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHistoryBmiBinding
    private val viewModels: HistoryViewModel by viewModels {
        HistoryViewModelFactory((application as App).messageRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val messageAdapter = HistoryAdapter {
            clickListener(it)
        }



        binding = ActivityHistoryBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar_bmi_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // set back button action
        toolbar_bmi_history.setNavigationOnClickListener {
            onBackPressed()
        }

        val result = intent.extras?.getString("NAME")

        if (result != null) {
            viewModels.addBmiToDatabase(result)
        }


        binding.historyRv?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.historyRv?.adapter = messageAdapter

        viewModels.bmiListLiveData.observe(this, Observer {
            messageAdapter.submitList(it)
        })
    }

    private fun clickListener(message: BMI) {

        viewModels.deleteMessage(message)
    }


}