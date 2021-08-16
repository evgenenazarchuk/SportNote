package com.evgenynaz.sportnote.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.evgenynaz.sportnote.R
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi.toolbar_bmi_activity
import kotlinx.android.synthetic.main.activity_note_details.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // вернуться назад
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        calculate_units_btn.setOnClickListener {
                if (validateMetricUnits()) {
                    val heightValue: Float =
                        metric_unit_height_et.text.toString().toFloat() / 100 // convert to meters
                    val weightValue: Float = metric_unit_weight_et.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Пожалуйста введите корректные данные", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        display_bmi_result_ll.visibility = View.VISIBLE
        your_bmi_tv.visibility = View.VISIBLE
        bmi_result_tv.visibility = View.VISIBLE
        bmi_type_tv.visibility = View.VISIBLE
        bmi_description_tv.visibility = View.VISIBLE


        //округление данных до 2 значений
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //set updated text
        bmi_result_tv.text = bmiValue
        bmi_type_tv.text = bmiLabel
        bmi_description_tv.text = bmiDescription
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
