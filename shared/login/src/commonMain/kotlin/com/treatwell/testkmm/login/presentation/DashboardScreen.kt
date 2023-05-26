package com.treatwell.testkmm.login.presentation

import com.treatwell.testkmm.login.domain.usecase.FetchUserUseCase
import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogOutUseCase

interface IDashboardScreenViewModel {
    var __uiState: DashboardScreenUIState
    val isUserLoggedInUseCase: IsUserLoggedInUseCase
    val logOutUseCase: LogOutUseCase
    val fetchUserUseCase: FetchUserUseCase
    val userActions: DashboardScreenUserActions
        get() = DashboardScreenUserActions(
            onGoLogInButtonClicked = ::goToLogin,
            onLogOutButtonClicked = ::logout
        )

    fun logout() {
        launchInViewModelScope{
            logOutUseCase()
            __uiState = __uiState.copy(showLoggedInView = isUserLoggedInUseCase())
        }
    }

    fun goToLogin() {
        sendSideEffect(DashboardScreenSideEffect.GoToLoginScreen)
    }

    fun sendSideEffect(sideEffect: DashboardScreenSideEffect)
    fun launchInViewModelScope(function: suspend () -> Unit)
}

// Use this for iOs automatic implementation
abstract class DashboardScreenViewModel : IDashboardScreenViewModel

data class DashboardScreenUIState(
    val showLoggedInView: Boolean = false
)

data class DashboardScreenUserActions(
    val onGoLogInButtonClicked: () -> Unit,
    val onLogOutButtonClicked: () -> Unit,
)

sealed class DashboardScreenSideEffect {
    object GoToLoginScreen : DashboardScreenSideEffect()
}