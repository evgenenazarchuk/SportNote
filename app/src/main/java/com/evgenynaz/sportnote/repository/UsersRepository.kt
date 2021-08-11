package com.evgenynaz.sportnote.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.evgenynaz.sportnote.data.UserDao
import com.evgenynaz.sportnote.datastore.AppSettings
import com.evgenynaz.sportnote.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UsersRepository(
    context: Context,
    private val usersDao: UserDao,
    private val appSettings: AppSettings,
   // private val broadcastRepository: BroadcastRepository
) {
    val allUserNames = usersDao.getAllUserNames()

    suspend fun login(userName: String) {
        withContext(Dispatchers.IO) { //указываем, что метод должен выполниться в IO
            if (checkUserExists(userName).not()) { //проверяем существует ли в базе юзер с таким именем
                usersDao.newUser(User(name = userName)) //добавляем в базу нового юзера, берем его сгенерированный базой id
                appSettings.setUserName(userName) //запоминаем залогиненного юзера в сеттингах
            } else {
                appSettings.setUserName(userName)
            }
        }
       // broadcastRepository.broadcastNotesUpdate()
    }

    private suspend fun checkUserExists(userName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUsersCount(userName) > 0
        }
    }

    fun getCurrentUserFlow(): Flow<User> = appSettings.userNameFlow().map { User(it) }

    fun checkUserLoggedIn(): Flow<Boolean> =
        appSettings.userNameFlow().map { it.isNotEmpty() }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            appSettings.setUserName("")
        }
      //  broadcastRepository.broadcastNotesUpdate()
    }

    suspend fun deleteCurrent() {
        withContext(Dispatchers.IO) {
            usersDao.deleteUser(User(appSettings.userName()))
            logout()
        }
    }

    @SuppressLint("HardwareIds")
    val phoneId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}