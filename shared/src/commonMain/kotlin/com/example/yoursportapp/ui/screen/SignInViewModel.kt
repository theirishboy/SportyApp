package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.ressources.RegexRules
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignInUiState(
    var email : String = "",
    var password : String = ""
)
class SignInViewModel(private val userDao: UserDatabaseDAO) : ViewModel() {
    var _signInUiState = MutableStateFlow(SignInUiState())
        private set
    suspend fun signIn(){
         userDao.signIn(_signInUiState.value.email,_signInUiState.value.password)
    }
    fun onEmailChange(newEmail : String){
        _signInUiState.update { it.copy(email = newEmail) }

    }
    fun onPasswordChange(newPassword : String){
        _signInUiState.update { it.copy(password = newPassword) }    }
    fun isEmailValid(): Boolean {
        if (_signInUiState.value.email.matches(RegexRules.emailAddressRegex)){
            return true
        }
        return false
    }
}