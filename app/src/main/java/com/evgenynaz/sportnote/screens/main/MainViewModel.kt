package com.evgenynaz.sportnote.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.evgenynaz.sportnote.App
import com.evgenynaz.sportnote.model.Note


class MainViewModel : ViewModel() {
    val noteLiveData: LiveData<List<Note>>? = App.instance?.noteDao?.allLiveData
}