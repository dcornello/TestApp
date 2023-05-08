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

    private fun goToLogin() {
        sendSideEffect(DashboardScreenSideEffect.GoToLoginScreen)
    }

    abstract fun sendSideEffect(sideEffect: DashboardScreenSideEffect)
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