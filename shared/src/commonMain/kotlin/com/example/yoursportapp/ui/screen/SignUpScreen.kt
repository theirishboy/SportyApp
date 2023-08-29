package com.example.yoursportapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.runtime.sourceInformationMarkerStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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


data class SignUpScreen(val postId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel(Unit, viewModelFactory { SignUpViewModel(UserDatabaseDAO()) })

        SignUpForm(navigator, viewModel)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpForm(navigator: Navigator, viewModel: SignUpViewModel) {
    var errorMessage by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(true) }

    val focus = LocalFocusManager.current

    val signUpUiState by viewModel._signUpUiState.collectAsState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    @Composable
    fun TermsAndConditions() {
        val fullText = "I accept the Terms & Conditions"
        val clickableText = "Terms & Conditions"
        val tag = "terms-and-conditions"

        val annotatedString = buildAnnotatedString {
            append(fullText)
            val start = fullText.indexOf(clickableText)
            val end = start + clickableText.length

            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                ),
                start = start,
                end = end
            )

            addStringAnnotation(
                tag = tag,
                annotation = "https://www.google.com",
                start = start,
                end = end
            )
        }
        val uriHandler = LocalUriHandler.current
        ClickableText(
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LocalContentColor.current
            ),
            text = annotatedString,
            onClick = { offset ->
                annotatedString
                    .getStringAnnotations(tag, offset, offset)
                    .firstOrNull()
                    ?.let { string ->
                        uriHandler.openUri(string.item)
                    }
            }
        )
    }

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
                text = "Create Free Account",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signUpUiState.firstname,
                label = { Text("first name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = { viewModel.onFirstNameChange(newFirstName = it)  },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signUpUiState.lastname,
                label = { Text("last name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = { viewModel.onLastNameChange(newLastName = it)  },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signUpUiState.email,
                label = { Text("E-mail") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focus.moveFocus(FocusDirection.Next)
                    }
                ),
                onValueChange = { viewModel.onEmailChange(newEmail = it)},
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
                value = signUpUiState.password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focus.clearFocus()
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
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable(interactionSource,
                        indication = rememberRipple(),
                        onClick = {
                            acceptedTerms = acceptedTerms.not()
                        })
                    .padding(horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = acceptedTerms,
                    onCheckedChange = {
                        acceptedTerms = it
                    },
                    interactionSource = interactionSource
                )
                TermsAndConditions()
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Button(
                    enabled =  viewModel.isEmailValid() && acceptedTerms && signUpUiState.firstname.isNotBlank()
                            && signUpUiState.password.isNotBlank()
                            && signUpUiState.email.isNotBlank()
                           ,
                    onClick = { coroutineScope.launch { viewModel.onSignUp()}},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign Up")
                }

                Spacer(Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { navigator.push(SignInScreen(0))},
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.66f
                        )
                    )
                ) {
                    Text("Already have an account? Sign in")
                }
            }
        }
    }
}