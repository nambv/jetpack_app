package com.nambv.jetpack_app.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nambv.jetpack_app.repositories.AuthRepository

var username = mutableStateOf("")
    private set

var usernameError = mutableStateOf<String?>(null)
    private set

var password = mutableStateOf("")
    private set

var passwordError = mutableStateOf<String?>(null)
    private set


var isFormValid = mutableStateOf(false)
    private set

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navigateToHome: () -> Unit) {
    val state by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth(),
            isError = usernameError.value != null
        )
        usernameError.value?.let {
            Text(text = it, color = MaterialTheme.colors.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.value != null
        )
        passwordError.value?.let {
            Text(text = it, color = MaterialTheme.colors.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                validateForm()
                if (isFormValid.value) {
                    viewModel.login(username.value, password.value)
                }
            }) {
            Text(text = "Login")
        }
        if (state.isLoggedIn) {
            navigateToHome()
        }
    }
}

fun validateForm() {
    usernameError.value = if (username.value.isBlank()) "Username is required" else null
    passwordError.value = if (password.value.isBlank()) "Password is required" else null

    isFormValid.value = usernameError.value == null && passwordError.value == null
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val previewViewModel = remember { LoginViewModel(AuthRepository()) }
    LoginScreen(viewModel = previewViewModel, navigateToHome = {})
}