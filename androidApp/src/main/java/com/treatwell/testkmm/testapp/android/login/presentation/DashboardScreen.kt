package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreenDestination() {
    val viewModel: DashboardScreenViewModel = koinViewModel()
    DashboardScreen()
}

@Composable
fun DashboardScreen() {
    Column {
        Text(text = "")
        Button(onClick = { /*TODO*/ }) {

        }
    }
}