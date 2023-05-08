package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import com.treatwell.testkmm.login.domain.usecase.FetchUserUseCase
import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogOutUseCase
import com.treatwell.testkmm.login.presentation.DashboardScreenSideEffect
import com.treatwell.testkmm.login.presentation.DashboardScreenUIState
import com.treatwell.testkmm.login.presentation.IDashboardScreenViewModel

class DashboardScreenViewModel(
    override val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    override val logOutUseCase: LogOutUseCase,
    override val fetchUserUseCase: FetchUserUseCase
) : ViewModel(), IDashboardScreenViewModel {

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun sendSideEffect(sideEffect: DashboardScreenSideEffect) {
        TODO("Not yet implemented")
    }

    override fun updateUiState(uiState: DashboardScreenUIState) {
        TODO("Not yet implemented")
    }
}