package com.evgenynaz.sportnote

import android.app.Application
import androidx.room.Room
import com.evgenynaz.sportnote.bmi.HistoryViewModel
import com.evgenynaz.sportnote.bmi.database.BmiDatabase
import com.evgenynaz.sportnote.bmi.database.BmiRepository
import com.evgenynaz.sportnote.bmi.database.DatabaseConstructor
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


   /* val messageDatabase: BmiDatabase by lazy {
        Room.databaseBuilder(this, BmiDatabase::class.java, "message_database").build()
    }
    val messageDao: BmiDao by lazy { messageDatabase.BmiDao() }

    val messageRepository: BmiRepository by lazy { BmiRepository(messageDao) }*/

   /* var database: AppDatabase? = null
    var noteDao: NoteDao? = null*/

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModels, repository, api,storageModule))
        }

      /*  instance = this
        val also = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-db-name" //получает доступ к базе в основном потоке надо переделать в бэкраунд
        )
            .allowMainThreadQueries()
            .build().also { database = it }
        noteDao = database!!.noteDao()*/

    }


    private val viewModels = module {
        viewModel { WeatherViewModel(get()) }
        viewModel { HistoryViewModel(get()) }
    }
    private val repository = module {
        factory { ApiRepository(get()) }
        factory { BmiRepository(get()) }
    }

    private val api = module {
        single { WeatherApi.get() }

    }

    //koin
    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }  //создаем синглтон базы данных
        factory { get<BmiDatabase>().BmiDao() } //предоставляем доступ для конкретной Dao (в нашем случае NotesDao)
    }

    /*companion object {
        var instance: App? = null
            private set

    }*/
}