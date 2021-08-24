package com.evgenynaz.sportnote.bmi
import androidx.lifecycle.*
import com.evgenynaz.sportnote.bmi.database.BmiEntity
import com.evgenynaz.sportnote.bmi.database.BmiRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(
    private val bmiRepository: BmiRepository
) : ViewModel() {

    //    private val messageRepository: MessageRepository by inject()
    val bmiListLiveData: LiveData<List<BMI>> =
        bmiRepository.getMessagesList().asLiveData()

    fun addBmiToDatabase(bmi: String) {

        val newBmi = BmiEntity(
            bmi,
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(System.currentTimeMillis())
        )

        viewModelScope.launch {
            bmiRepository.addMessages(newBmi)
        }
    }

    fun deleteMessage(message: BMI) {
        viewModelScope.launch {
            bmiRepository.deleteMessage(message)
        }
    }
}

/*class HistoryViewModelFactory(
    private val messageRepository: BmiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(messageRepository) as T
    }
}*/

