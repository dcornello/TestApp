package com.treatwell.testkmm.login.presentation

import androidx.lifecycle.ViewModel
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase

class LoginScreenViewModel : ViewModel() {

    val signUpUseCase = SignUpUseCase()

    fun createAccount(){
        viewModelScope.launch(CoroutineDispatcher.io) {
            signUpUseCase()
        }
    }
}