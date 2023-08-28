package com.example.yoursportapp.ui.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.yoursportapp.data.UserDatabaseDAO
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class SignInScreen(val postId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel(Unit, viewModelFactory { SignInViewModel(UserDatabaseDAO()) })

        SignInForm(navigator,viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInForm(navigator: Navigator, viewModel: SignInViewModel,
              // onValueChange: (SignInUiState) -> Unit = viewModel::updateUiState,
){

        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        var errorMessage by remember { mutableStateOf("") }
        var acceptedTerms by remember { mutableStateOf(true) }
        val signInUiState by viewModel._signInUiState.collectAsState()
        val focus = LocalFocusManager.current
        // val keyboardController = LocalSoftwareKeyboardController.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp)
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Log in",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )


                Spacer(Modifier.height(12.dp))

                Spacer(Modifier.height(24.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = signInUiState.username,
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focus.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onValueChange = { viewModel.onUsernameChange(newUsername = it) },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    isError = errorMessage.isNotBlank(),
                    supportingText = {
                        Text(errorMessage)
                    },
                    value = signInUiState.password,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focus.clearFocus()
                            //  keyboardController?.hide()
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        viewModel.onPasswordChange(newPassword = it)
                    },
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))
                val interactionSource = remember { MutableInteractionSource() }

                Spacer(Modifier.height(16.dp))
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Button(
                        enabled = acceptedTerms && signInUiState.username.isNotBlank()
                                && signInUiState.password.isNotBlank(),
                        onClick = { coroutineScope.launch { viewModel.signIn() }},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { navigator.push(SignUpScreen(0)) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.66f
                            )
                        )
                    ) {
                        Text("Don't have an account? Sign up ")
                    }
                }
            }
        }
}
