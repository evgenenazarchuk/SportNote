package com.evgenynaz.sportnote.bmi.ui.listActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.evgenynaz.sportnote.bmi.database.UserBmi
import com.evgenynaz.sportnote.bmi.database.UserBmiDao
import com.evgenynaz.sportnote.bmi.room.MyRoom

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: UserBmiDao by lazy {
        MyRoom.myDatabase.userBmiDao()
    }


    fun getUpdateList(): LiveData<MutableList<UserBmi>> = dao.getAll()


    fun deleteBmiData(userBmi: UserBmi) {
        val result: Int = dao.deleteUserBmiData(userBmi)
        if (result == 0)
            throw IllegalAccessException("Invalid data to delete")
    }
}
