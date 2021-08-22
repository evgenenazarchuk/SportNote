package com.evgenynaz.sportnote.bmi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evgenynaz.sportnote.bmi.database.BmiDao
import com.evgenynaz.sportnote.bmi.database.BmiEntity

@Database(entities = [BmiEntity::class], version = 1)
abstract class BmiDatabase : RoomDatabase() {

    abstract fun BmiDao(): BmiDao
}

object DatabaseConstructor {
    fun create(context: Context): BmiDatabase =
        Room.databaseBuilder(
            context,
            BmiDatabase::class.java,
            "bmi_database"
        ).build()
}