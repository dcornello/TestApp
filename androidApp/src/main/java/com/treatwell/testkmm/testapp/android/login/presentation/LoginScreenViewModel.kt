package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    fun createAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase()
        }
    }
}