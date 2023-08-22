package com.example.yoursportapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.yoursportapp.data.UserDatabaseDAO
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignInUiState(
    var username : String = "",
    var password : String = ""
)
class SignInViewModel(private val userDao: UserDatabaseDAO) : ViewModel() {
    var _signInUiState = MutableStateFlow(SignInUiState())
        private set
    suspend fun signIn(){
        userDao.signIn(_signInUiState.value.username,_signInUiState.value.password)
    }
    fun onUsernameChange(newUsername : String){
        _signInUiState.value.username = newUsername
    }
    fun onPasswordChange(newPassword : String){
        _signInUiState.value.password = newPassword
    }
}