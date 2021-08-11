package com.evgenynaz.sportnote.bmi.ui.insertEditActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.evgenynaz.sportnote.bmi.database.UserBmi
import com.evgenynaz.sportnote.bmi.database.UserBmiDao
import com.evgenynaz.sportnote.bmi.room.MyRoom

class InsertEditViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: UserBmiDao by lazy {
        MyRoom.myDatabase.userBmiDao()
    }


    fun calculateBmi(height: String, weight: String): String {
        val h: Float = height.toFloat()
        val w: Float = weight.toFloat()

        return (w / (h * h)).toString()
    }

    fun insertBmiData(userBmi: UserBmi) =
            dao.insertUserBmiData(userBmi)

    fun updateBmiData(userBmi: UserBmi) =
            dao.updateUserBmiData(userBmi)

    fun fetchBmiData(id: Int) = dao.getBmiUserData(id)
}
