package com.evgenynaz.sportnote.bmi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(UserBmi::class)], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userBmiDao(): UserBmiDao
}