package com.evgenynaz.sportnote

import android.app.Application
import androidx.room.Room
import com.evgenynaz.sportnote.data.AppDatabase
import com.evgenynaz.sportnote.data.NoteDao


class App : Application() {
    var database: AppDatabase? = null
    var noteDao: NoteDao? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        val also = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-db-name" //получает доступ к базе в основном потоке надо переделать в бэкраунд
        )
            .allowMainThreadQueries()
            .build().also { database = it }
        noteDao = database!!.noteDao()
    }

    companion object {
        var instance: App? = null
            private set
    }
}