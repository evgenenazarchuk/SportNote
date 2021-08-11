package com.evgenynaz.sportnote.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenynaz.sportnote.App
import com.evgenynaz.sportnote.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance


class MainViewModel : ViewModel() {
    val noteLiveData: LiveData<List<Note>>? = App.instance?.noteDao?.allLiveData

   /* fun addMessageToDatabase(message: String) {

        val newMessage = NoteEntity(
            message,
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(System.currentTimeMillis())
        )

        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.addMessages(newMessage)
        }
    }*/
}