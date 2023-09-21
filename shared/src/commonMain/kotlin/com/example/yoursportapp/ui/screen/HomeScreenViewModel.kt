package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.SportSession
import com.example.yoursportapp.data.UserDatabaseDAO
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class HomeScreenUiState(
    val userName: String = "",
    var allSportSession: MutableList<SportSession> = arrayListOf()
)
class HomeScreenViewModel : ViewModel(), KoinComponent {
    private val userDao: UserDatabaseDAO by inject()
    var _homeScreenUiState = MutableStateFlow(HomeScreenUiState())
        private set



    init {
      //  getUser()
    }
    private fun getUser() {
    }
     suspend fun getAllSportSession(){
            _homeScreenUiState.update { it.copy(allSportSession = userDao.getAllSportSessionFromUser()) }

    }


}