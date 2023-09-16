package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.connectcoins.ui.ConfigScreen
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.GameScreen
import com.example.connectcoins.ui.Screen
import com.example.connectcoins.ui.theme.ConnectCoinsTheme


class MainActivity : ComponentActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConnectCoinsTheme {
                gameViewModel = viewModel()

                //////////////////////// NAVIGATION
                val navController = rememberNavController()
                //startDestination = Screen.ConfigScreen.route
                NavHost(navController = navController, startDestination = Screen.ConfigScreen.route) {
                    composable(route = Screen.GameScreen.route) {
                        GameScreen(gameViewModel, navController)//TopBar(navController) //MainScreen(gameViewModel = gameViewModel)
                    }
                    composable(
                        route = Screen.ConfigScreen.route + "?name={name}",//"/{name}", //"?name={name}",
                        arguments = listOf(
                            navArgument("name") {
                                type = NavType.StringType
                                defaultValue = "Player :D"
                                nullable = true
                            }
                        )
                    ) { entry ->
                        ConfigScreen(name = entry.arguments?.getString("name"), gameViewModel, navController)
                    }
                }

                //////////////////////////////////
                //MainScreen(gameViewModel)
                //ConfigScreen(viewModel = gameViewModel, navController = navController)
            }
        }
    }
}





