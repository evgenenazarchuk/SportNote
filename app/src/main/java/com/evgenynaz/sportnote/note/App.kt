package com.evgenynaz.sportnote.note

import android.app.Application
import androidx.room.Room
import com.evgenynaz.sportnote.bmi.BmiDatabase
import com.evgenynaz.sportnote.bmi.database.BmiDao
import com.evgenynaz.sportnote.bmi.database.BmiRepository
import com.evgenynaz.sportnote.note.data.AppDatabase
import com.evgenynaz.sportnote.note.data.NoteDao
import com.evgenynaz.sportnote.weather.WeatherViewModel
import com.evgenynaz.sportnote.weather.restApi.ApiRepository
import com.evgenynaz.sportnote.weather.restApi.WeatherApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

@KoinApiExtension
class App : Application(), KoinComponent {


    val messageDatabase: BmiDatabase by lazy {
        Room.databaseBuilder(this, BmiDatabase::class.java, "message_database").build()
    }
    val messageDao: BmiDao by lazy { messageDatabase.BmiDao() }

    val messageRepository: BmiRepository by lazy { BmiRepository(messageDao) }

    var database: AppDatabase? = null
    var noteDao: NoteDao? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModels, repository, api))
        }

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


    private val viewModels = module {
        viewModel { WeatherViewModel(get()) }
        // viewModel { NoteFragmentViewModel(get()) }
    }
    private val repository = module {
        factory { ApiRepository(get()) }
        //    factory { NoteRepository(get()) }
    }

    private val api = module {
        single { WeatherApi.get() }

    }

    companion object {
        var instance: App? = null
            private set

    }
}