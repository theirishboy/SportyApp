package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.SignInResult
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.data.UserToken
import com.example.yoursportapp.ressources.RegexRules
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignInUiState(
    var email : String = "",
    var password : String = "",
    var isConnected : Boolean = true,
    var errorMessage: String = "",
)
class SignInViewModel(private val userDao: UserDatabaseDAO) : ViewModel() {
    var _signInUiState = MutableStateFlow(SignInUiState())
        private set
    suspend fun signIn(){
        val result = userDao.signIn(_signInUiState.value.email,_signInUiState.value.password)
        when(result) {
            is SignInResult.Success -> {
                println("Signed in successfully.")
                _signInUiState.update { it.copy(isConnected = true) }
            }
            is SignInResult.Error -> {
                println("Sign-in failed: ${result.errorMessage}")
                _signInUiState.update { it.copy(errorMessage = result.errorMessage) }
            }
        }
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