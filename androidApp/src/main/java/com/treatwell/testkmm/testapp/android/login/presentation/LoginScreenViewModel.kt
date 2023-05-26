package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.LogInUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import com.treatwell.testkmm.login.presentation.ILoginScreenViewModel
import com.treatwell.testkmm.login.presentation.LoginScreenSideEffect
import com.treatwell.testkmm.login.presentation.LoginScreenUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    override val signUpUseCase: SignUpUseCase,
    override val emailValidationUseCase: EmailValidationUseCase,
    override val passwordValidationUseCase: PasswordValidationUseCase,
    override val logInUseCase: LogInUseCase
) : ViewModel(), ILoginScreenViewModel {

    override var __uiState: LoginScreenUIState
        get() = _viewState.value
        set(newState) {
            _viewState.update { newState }
        }


    private val _viewState = MutableStateFlow(
        LoginScreenUIState()
    )
    val viewState = _viewState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<LoginScreenSideEffect>()
    val sideEffects: Flow<LoginScreenSideEffect> = _sideEffects.asSharedFlow()

    override fun sendSideEffect(sideEffect: LoginScreenSideEffect) {
        viewModelScope.launch { _sideEffects.emit(sideEffect) }
    }

    override fun launchInViewModelScope(function: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            function()
        }
    }
}