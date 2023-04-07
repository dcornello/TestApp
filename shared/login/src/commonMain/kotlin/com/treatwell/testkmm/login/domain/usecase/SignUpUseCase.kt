package com.treatwell.testkmm.login.domain.usecase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth


class SignUpUseCase {
    suspend operator fun invoke(){
        Firebase.auth.createUserWithEmailAndPassword("diego.cornello@treatwell.com", "Password_123")
    }
}