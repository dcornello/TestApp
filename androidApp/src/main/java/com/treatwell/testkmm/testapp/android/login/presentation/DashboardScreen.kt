package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.treatwell.testkmm.login.presentation.DashboardScreenSideEffect
import com.treatwell.testkmm.login.presentation.DashboardScreenUIState
import com.treatwell.testkmm.login.presentation.DashboardScreenUserActions
import com.treatwell.testkmm.testapp.android.login.presentation.LoginNavigationGraphRoute.LogInScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreenDestination(navController: NavHostController) {
    val viewModel: DashboardScreenViewModel = koinViewModel()
    val uiState by viewModel.viewState.collectAsState()

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val lifecycleObserver = remember(viewModel) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> viewModel.update()
                else -> Unit
            }
        }
    }

    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffects.onEach {
            when (it) {
                is DashboardScreenSideEffect.GoToLoginScreen -> navController.navigate(LogInScreen)
            }
        }.collect()
    }
    DashboardScreen(uiState, viewModel.userActions)
}

@Composable
fun DashboardScreen(uiState: DashboardScreenUIState, userActions: DashboardScreenUserActions) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "I'm an Android App")
        if (uiState.showLoggedInView) {
            Button(onClick = userActions.onLogOutButtonClicked) {
                Text(text = "Logout")
            }
        } else {
            Button(onClick = userActions.onGoLogInButtonClicked) {
                Text(text = "goToLogin")
            }
        }
    }
}