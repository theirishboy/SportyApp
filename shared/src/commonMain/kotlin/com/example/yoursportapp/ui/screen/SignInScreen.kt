package com.example.yoursportapp.ui.screen

import YourSportApp.shared.SharedRes
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class SignInScreen(val postId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel(Unit, viewModelFactory { SignInViewModel() })
        SignInForm(navigator,viewModel)
    }
}

@Composable
fun SignInForm(navigator: Navigator, viewModel: SignInViewModel,
              // onValueChange: (SignInUiState) -> Unit = viewModel::updateUiState,
){

        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        var errorMessage by remember { mutableStateOf("") }
        var acceptedTerms by remember { mutableStateOf(true) }
        val signInUiState by viewModel._signInUiState.collectAsState()
        val focus = LocalFocusManager.current
        if (signInUiState.isConnected) navigator.push(HomeScreen(1))
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

                SignInText()
                Spacer(Modifier.height(12.dp))
                Spacer(Modifier.height(24.dp))
                UserEmailTextField(signInUiState, focus, viewModel)
                Spacer(Modifier.height(8.dp))
                UserPasswordField(errorMessage, signInUiState, focus, viewModel)
                if (signInUiState.errorMessage == "")  Spacer(Modifier.height(16.dp)) else
                    signInErrorMessage(signInUiState)
                SignInButton(acceptedTerms, signInUiState, viewModel, coroutineScope, navigator)
            }
        }
}

@Composable
fun signInErrorMessage(signInUiState: SignInUiState) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = signInUiState.errorMessage,
        color = Color.Red )
    Spacer(Modifier.height(16.dp))
}

@Composable
fun SignInText() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(SharedRes.strings.sign_In),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun UserEmailTextField(
    signInUiState: SignInUiState,
    focus: FocusManager,
    viewModel: SignInViewModel
) {
    StandardTextField(
        errorMessage = "",
        content = signInUiState.email,
        focus,
        label = stringResource(SharedRes.strings.e_mail),
        onValueChange =  {viewModel.onEmailChange(newEmail = it)}
    )

}

@Composable
fun UserPasswordField(
    errorMessage: String,
    signInUiState: SignInUiState,
    focus: FocusManager,
    viewModel: SignInViewModel
) {
    StandardTextField(errorMessage,
        content = signInUiState.password,
        focus,
        label = stringResource(SharedRes.strings.password),
        onValueChange = {viewModel.onPasswordChange(newPassword = it)},
        visualTransformation = PasswordVisualTransformation()
    )
}
@Composable
fun SignInButton(
    acceptedTerms: Boolean,
    signInUiState: SignInUiState,
    viewModel: SignInViewModel,
    coroutineScope: CoroutineScope,
    navigator: Navigator
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Button(
            enabled = acceptedTerms && signInUiState.email.isNotBlank()
                    && signInUiState.password.isNotBlank() && viewModel.isEmailValid(),
            onClick = { coroutineScope.launch { viewModel.signIn() } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(SharedRes.strings.sign_In))
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
            Text(stringResource(SharedRes.strings.no_account))
        }
    }
}

@Composable
fun StandardTextField(
    errorMessage: String,
    content: String,
    focus: FocusManager,
    label: String,
    onValueChange:(String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        isError = errorMessage.isNotBlank(),
        supportingText = {
            Text(errorMessage)
        },
        value = content,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focus.clearFocus()
            }
        ),
        visualTransformation = visualTransformation,
        onValueChange = onValueChange,
        singleLine = true
    )
}

