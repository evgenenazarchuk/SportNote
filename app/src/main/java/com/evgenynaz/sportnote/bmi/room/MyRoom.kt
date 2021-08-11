package com.evgenynaz.sportnote.bmi.room

import android.app.Application
import androidx.room.Room
import com.evgenynaz.sportnote.bmi.database.MyDatabase

/**
 * Created by aniket on 17-09-2017.
 */
class MyRoom : Application() {
    companion object {
        lateinit var myDatabase: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        myDatabase = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            "Bmi-MyDatabase"
        ).allowMainThreadQueries()
            .build()
    }
}
