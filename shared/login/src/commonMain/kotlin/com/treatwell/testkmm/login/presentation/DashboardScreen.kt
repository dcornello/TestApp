package com.treatwell.testkmm.login.presentation

import com.treatwell.testkmm.login.domain.usecase.FetchUserUseCase
import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogOutUseCase

abstract class DashboardScreenViewModelPact {

    abstract val isUserLoggedInUseCase: IsUserLoggedInUseCase
    abstract val logOutUseCase: LogOutUseCase
    abstract val fetchUserUseCase: FetchUserUseCase

//    fun getUserActions(): DashboardScreenUserActions {
//        return DashboardScreenUserActions(
//            onGoLogInButtonClicked = ::goToLogin,
//            onLogOutButtonClicked = ::logout
//        )
//    }

    val userActions: DashboardScreenUserActions =
        DashboardScreenUserActions(
            onGoLogInButtonClicked = ::goToLogin,
            onLogOutButtonClicked = ::logout
        )

    private fun goToLogin() {
        sendSideEffect(DashboardScreenSideEffect.GoToLoginScreen)
    }

    abstract fun logout()

    abstract fun sendSideEffect(sideEffect: DashboardScreenSideEffect)

    abstract fun updateUiState(uiState: DashboardScreenUIState)
}

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