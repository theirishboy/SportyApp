package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.UserDatabaseDAO
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignUpUiState( val username: String = "",
                           val password: String= "",
                           val email: String= "",
                           val termsStatus : Boolean = false)

class SignUpViewModel(private val userDao: UserDatabaseDAO) : ViewModel() {
    var _signUpUiState = MutableStateFlow(SignUpUiState())
        private set
    fun onSignUp(){
        //userDao.signUp()
    }
    fun isEmailValid(): Boolean {
        if (_signUpUiState.value.email.matches(emailAddressRegex)){
            return true
        }
        return false
    }
    fun onUsernameChange(newUsername : String){
        _signUpUiState.update { it.copy(username = newUsername) }

    }
    fun onPasswordChange(newPassword : String){
        _signUpUiState.update { it.copy(password = newPassword) }    }

    fun onEmailChange(newEmail : String){
        _signUpUiState.update { it.copy(email = newEmail) }    }
}
private val emailAddressRegex = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
