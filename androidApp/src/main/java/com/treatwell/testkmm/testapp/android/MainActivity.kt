package com.treatwell.testkmm.testapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.treatwell.testkmm.testapp.Greeting
import com.treatwell.testkmm.testapp.android.login.presentation.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: LoginScreenViewModel = koinViewModel()
                    GreetingView(Greeting().greet(), viewModel::createAccount)
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String, onClick: () -> Unit) {
    Text(text = text)
    Button(onClick = onClick) {
        Text(text = "SignUp")
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!", {})
    }
}
