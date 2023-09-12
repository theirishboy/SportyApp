package com.example.yoursportapp.ui.screen

import com.example.yoursportapp.data.SignInResult
import com.example.yoursportapp.data.SignUpResult
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.ressources.RegexRules
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SignUpUiState( val firstname: String = "",
                           val lastname: String= "",
                           val email: String= "",
                           val password : String = "",
                           val termsStatus : Boolean = false,
                          var isRegistered : Boolean = false,
                          var errorMessage: String = "",
)

class SignUpViewModel() : ViewModel(), KoinComponent {
    private val userDao: UserDatabaseDAO by inject()

    var _signUpUiState = MutableStateFlow(SignUpUiState())
        private set
    suspend fun onSignUp(){
        val result = userDao.signUp(_signUpUiState.value.firstname,_signUpUiState.value.lastname,_signUpUiState.value.email,_signUpUiState.value.password)

        when(result) {
            is SignUpResult.Success -> {
                println("Signed in successfully.")
                _signUpUiState.update { it.copy(isRegistered = true) }
            }
            is SignUpResult.Error -> {
                println("Sign-in failed: ${result.errorMessage}")
                _signUpUiState.update { it.copy(errorMessage = result.errorMessage) }
            }
        }
    }
    fun isEmailValid(): Boolean {
        if (_signUpUiState.value.email.matches(RegexRules.emailAddressRegex)){
            return true
        }
        return false
    }
    fun onFirstNameChange(newFirstName : String){
        _signUpUiState.update { it.copy(firstname = newFirstName) }

    }
    fun onLastNameChange(newLastName : String){
        _signUpUiState.update { it.copy(lastname = newLastName) }

    }
    fun onPasswordChange(newPassword : String){
        _signUpUiState.update { it.copy(password = newPassword) }    }

    fun onEmailChange(newEmail : String){
        _signUpUiState.update { it.copy(email = newEmail) }    }
}
