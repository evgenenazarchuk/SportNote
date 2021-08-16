package com.evgenynaz.sportnote.bmi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BmiEntity::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun BmiDao(): BmiDao
}

object DatabaseConstructor {
    fun create(context: Context): MessageDatabase =
        Room.databaseBuilder(
            context,
            MessageDatabase::class.java,
            "bmi_database"
        ).build()
}