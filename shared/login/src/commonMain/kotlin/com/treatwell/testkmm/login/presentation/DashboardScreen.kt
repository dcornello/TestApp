package com.treatwell.testkmm.login.presentation

import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase

abstract class DashboardScreenViewModelPact {

    abstract val isUserLoggedInUseCase: IsUserLoggedInUseCase

    lateinit var uiState: DashboardScreenUIState

    fun getUserActions(): DashboardScreenUserActions {
        return DashboardScreenUserActions(
            onGoLogInButtonClicked = ::goToLogin
        )
    }

    fun goToLogin() {
        sendSideEffect(DashboardScreenSideEffect.GoToLoginScreen)
    }

    abstract fun sendSideEffect(sideEffect: DashboardScreenSideEffect) // TODO remove this from interface and make it private?
}

data class DashboardScreenUIState(
    val showLoggedInView: Boolean = false
)

data class DashboardScreenUserActions(
    val onGoLogInButtonClicked: () -> Unit,
)

sealed class DashboardScreenSideEffect {
    object GoToLoginScreen : DashboardScreenSideEffect()
}