package com.evgenynaz.sportnote.note.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.evgenynaz.sportnote.App
import com.evgenynaz.sportnote.note.model.Note


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