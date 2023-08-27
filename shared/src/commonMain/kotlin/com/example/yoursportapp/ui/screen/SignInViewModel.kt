package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.UserDatabaseDAO
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignInUiState(
    var username : String = "",
    var password : String = ""
)
class SignInViewModel(private val userDao: UserDatabaseDAO) : ViewModel() {
    var _signInUiState = MutableStateFlow(SignInUiState())
        private set
    suspend fun signIn(){
       // userDao.greeting()
         userDao.signIn(_signInUiState.value.username,_signInUiState.value.password)
    }
    fun onUsernameChange(newUsername : String){
        _signInUiState.update { it.copy(username = newUsername) }

    }
    fun onPasswordChange(newPassword : String){
        _signInUiState.update { it.copy(password = newPassword) }    }
}