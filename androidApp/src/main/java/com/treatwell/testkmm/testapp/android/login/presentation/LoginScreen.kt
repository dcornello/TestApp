package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.treatwell.testkmm.login.presentation.LoginScreenSideEffect
import com.treatwell.testkmm.login.presentation.LoginScreenUIState
import com.treatwell.testkmm.login.presentation.LoginScreenUserActions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenDestination(navController: NavHostController) {
    val viewModel: LoginScreenViewModel = koinViewModel()
    val uiState by viewModel.viewState.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(viewModel) {
        viewModel.sideEffects.onEach {
            when (it) {
                is LoginScreenSideEffect.GoToLogoutScreen -> navController.popBackStack()
                LoginScreenSideEffect.ShowLogInError -> {
                    keyboardController?.hide()
                    scaffoldState.snackbarHostState.showSnackbar("Opps something was wrong")
                }
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            if (uiState.showLoading)
                ProgressFullScreen()
            else
                LoginScreen(modifier = Modifier.padding(it), uiState = uiState, userActions = viewModel.userActions)
        },
        snackbarHost = {
            SnackbarHost(hostState = it, modifier = Modifier.padding(12.dp)) { snackbarData: SnackbarData ->
                Snackbar(
                    backgroundColor = Color.Red
                ) {
                    Text(text = snackbarData.message, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    )
}

@Composable
fun ProgressFullScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier, uiState: LoginScreenUIState, userActions: LoginScreenUserActions) {

    Column(
        modifier = modifier
            .padding(34.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 24.dp), text = "LoginScreen")
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = uiState.email, onValueChange = userActions.onEmailValueChanged, isError = uiState.emailError != null, placeholder = { Text(text = "Login") })
            uiState.emailError?.let { emailError ->
                Text(text = emailError.name, fontSize = 12.sp, color = Color.Red)
            }

            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = uiState.password, onValueChange = userActions.onPasswordValueChanged, isError = uiState.passwordError != null, placeholder = { Text(text = "Password") }, visualTransformation = PasswordVisualTransformation())
            uiState.passwordError?.let { passwordError ->
                Text(text = passwordError.name, fontSize = 12.sp, color = Color.Red)
            }
        }
        Button(onClick = userActions.onSignupClicked) {
            Text(text = "LOGIN")
        }
    }
}