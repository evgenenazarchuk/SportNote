package com.evgenynaz.sportnote.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.evgenynaz.sportnote.R
import com.evgenynaz.sportnote.databinding.ActivityBmiBinding
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi.toolbar_bmi_activity
import kotlinx.android.synthetic.main.activity_note_details.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // вернуться назад
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.histiryBtn.setOnClickListener{
                val intent3 = Intent(this, HistoryActivity::class.java)
                startActivity(intent3)
            }

        binding.calculateUnitsBtn.setOnClickListener {
            if (validateMetricUnits()) {
                val heightValue: Float =
                    binding.metricUnitHeightEt.text.toString().toFloat() / 100
                val weightValue: Float = binding.metricUnitWeightEt.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Пожалуйста введите корректные данные", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        display_bmi_result_ll.visibility = View.GONE
    }


    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Очень сильно пониженный вес"
            bmiDescription = "Ой! Вам действительно нужно лучше заботиться о себе! Ешь больше!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Сильно недостаточный вес"
            bmiDescription = "Упс! Тебе действительно нужно лучше заботиться о себе! Ешь больше!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Недостаточный вес"
            bmiDescription = "Ой! Вам действительно нужно лучше заботиться о себе! Ешь больше!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Нормальный"
            bmiDescription = "Поздравляю! Вы в хорошей форме!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Избыточный вес"
            bmiDescription = "Ой! Вам действительно нужно позаботиться о себе! Может быть, тренировка!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Класс ожирения | (Умеренно ожирение)"
            bmiDescription = "Ой! Вам действительно нужно позаботиться о себе! Может быть, тренировка!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Класс ожирения || (Сильно ожирение)"
            bmiDescription = "Вы в очень опасном состоянии! Действовать сейчас!"
        } else {
            bmiLabel = "Класс ожирения ||| (Очень сильно ожирение)"
            bmiDescription = "Вы в очень опасном состоянии!"
        }

        display_bmi_result_ll.visibility = View.VISIBLE
        binding.displayBmiResultLl.visibility = View.VISIBLE
        binding.yourBmiTv.visibility = View.VISIBLE
        binding.bmiResultTv.visibility = View.VISIBLE
        binding.bmiTypeTv.visibility = View.VISIBLE
        binding.bmiDescriptionTv.visibility = View.VISIBLE


        //округление данных до 2 значений
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //set updated text
        binding.bmiResultTv.text = bmiValue
        binding.bmiTypeTv.text = bmiLabel
        binding.bmiDescriptionTv.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (metric_unit_weight_et.text.toString().isEmpty() || metric_unit_height_et.text.toString()
                .isEmpty()
        ) {
            isValid = false
        }


        return isValid
    }

}
