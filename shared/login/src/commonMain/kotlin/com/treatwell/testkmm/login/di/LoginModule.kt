package com.treatwell.testkmm.login.di

import com.treatwell.testkmm.login.data.repository.AuthenticationRepositoryImpl
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository
import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.FetchUserUseCase
import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogOutUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginModule = module {
    single<AuthenticationRepository> { AuthenticationRepositoryImpl() }
    singleOf(::SignUpUseCase)
    singleOf(::LogInUseCase)
    singleOf(::LogOutUseCase)
    singleOf(::IsUserLoggedInUseCase)
    singleOf(::FetchUserUseCase)
    singleOf(::EmailValidationUseCase)
    singleOf(::PasswordValidationUseCase)
}