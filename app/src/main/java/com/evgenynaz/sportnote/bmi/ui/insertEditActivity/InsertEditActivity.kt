package com.evgenynaz.sportnote.bmi.ui.insertEditActivity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.evgenynaz.sportnote.R
import com.evgenynaz.sportnote.bmi.Common
import com.evgenynaz.sportnote.bmi.database.UserBmi
import com.evgenynaz.sportnote.databinding.ActivityInsertEditBinding


class InsertEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertEditBinding
    private var gender: String = "male"
    private var actionUpdate: Boolean = false
    private var id: Int = 0

    private val insertEditViewModel by lazy {
        ViewModelProviders.of(this).get(InsertEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAllViews()
        var title = "Insert new"
        val intent: Intent? = intent
        if (intent != null && intent.hasExtra(Common.LIST_TO_EDIT_ACTIVITY_INTENT_ID)) {
            id = intent.getIntExtra("id", 0)
            actionUpdate = true
            if (id != 0) {
                title = "Edit"
                attachBmiDataToUi(insertEditViewModel.fetchBmiData(id))
            }
        }
        setTitle(title)


        binding.insertUpdateBtn.setOnClickListener {
            addBmiData()
        }

        binding.genderSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long) {
                val selectedItem = binding.genderSpinner.selectedItem.toString()
                gender = selectedItem
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
            }
        }


    }

    private fun bindAllViews() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.genderSpinner.adapter = adapter

        binding.nameEditText
        binding.heightEditText
        binding.weightEditText
        binding.ageEditText
        binding.insertUpdateBtn


    }


    private fun addBmiData() {
        val nameText = binding.nameEditText.text.toString()
        val heightText = binding.heightEditText.toString()
        val weightText = binding.weightEditText.toString()
        val ageText = binding.ageEditText.text.toString()
        if (TextUtils.isEmpty(nameText) or TextUtils.isEmpty(heightText) or TextUtils.isEmpty(
                weightText
            ) or TextUtils.isEmpty(ageText)
        ) {
            AlertDialog.Builder(this)
                .setMessage("Please fill all fileds")
                .setPositiveButton("ok", (DialogInterface.OnClickListener { _, _ -> }))
                .show()
            return
        }


        val userBmi = UserBmi(
            null,
            binding.nameEditText.text.toString(),
            heightText,
            weightText,
            insertEditViewModel.calculateBmi(heightText, weightText),
            gender,
            binding.ageEditText.text.toString().toInt()
        )
        if (!actionUpdate)
            if (insertEditViewModel.insertBmiData(userBmi) > 0)
                dataSubmitted()
            else
                dataSubmitFailed()
        else {
            userBmi.id = id
            if (insertEditViewModel.updateBmiData(userBmi) > 0)
                dataSubmitted()
            else
                dataSubmitFailed()
        }
    }


    /**
     * This function called after data is submitted
     * Shows data submitted toast and destroys this activity
     */
    private fun dataSubmitted() {
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG).show()
        finish()
    }


    private fun attachBmiDataToUi(userBmi: UserBmi) {
        binding.nameEditText.setText(userBmi.name)
        binding.heightEditText.toString()
        binding.weightEditText.toString()
        binding.ageEditText.setText(userBmi.age.toString())

        if (userBmi.gender == "Male")
            binding.genderSpinner.setSelection(0, true)
        else if (userBmi.gender == "Female")
            binding.genderSpinner.setSelection(1, true)

        binding.insertUpdateBtn.text = getString(R.string.update_btn_text)
    }


    private fun dataSubmitFailed() {
        Toast.makeText(this, getString(R.string.error_toast_msg), Toast.LENGTH_LONG).show()
    }
}
