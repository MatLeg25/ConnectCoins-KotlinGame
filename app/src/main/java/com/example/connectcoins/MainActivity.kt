package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.MainScreen
import com.example.connectcoins.ui.theme.ConnectCoinsTheme


class MainActivity : ComponentActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConnectCoinsTheme {
                gameViewModel = viewModel()
                MainScreen(gameViewModel)
            }
        }
    }
}





