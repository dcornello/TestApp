package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun LoginNavigationGraph(
    navController: NavHostController
){
    return NavHost(navController = navController, startDestination = LoginNavigationGraphRoute.DashboardScreen) {
        composable(LoginNavigationGraphRoute.DashboardScreen) {

        }
    }
}

object LoginNavigationGraphRoute {
    const val LogInScreen = "LogInScreen"
    const val SignUpScreen = "SignUpScreen"
    const val DashboardScreen = "DashboardScreen"
}