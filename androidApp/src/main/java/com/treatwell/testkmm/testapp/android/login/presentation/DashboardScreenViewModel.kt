package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treatwell.testkmm.login.domain.usecase.FetchUserUseCase
import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogOutUseCase
import com.treatwell.testkmm.login.presentation.DashboardScreenSideEffect
import com.treatwell.testkmm.login.presentation.DashboardScreenUIState
import com.treatwell.testkmm.login.presentation.IDashboardScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardScreenViewModel(
    override val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    override val logOutUseCase: LogOutUseCase,
    override val fetchUserUseCase: FetchUserUseCase
) : ViewModel(), IDashboardScreenViewModel {

    override var __uiState: DashboardScreenUIState
        get() = _viewState.value
        set(value) {
            _viewState.update { value }
        }

    private val _viewState = MutableStateFlow(
        DashboardScreenUIState()
    )
    val viewState = _viewState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<DashboardScreenSideEffect>()
    val sideEffects: Flow<DashboardScreenSideEffect> = _sideEffects.asSharedFlow()

    override fun sendSideEffect(sideEffect: DashboardScreenSideEffect) {
        viewModelScope.launch { _sideEffects.emit(sideEffect) }
    }

    override fun launchInViewModelScope(function: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            function()
        }
    }

    fun update() {
        __uiState = viewState.value.copy(showLoggedInView = isUserLoggedInUseCase())
    }
}