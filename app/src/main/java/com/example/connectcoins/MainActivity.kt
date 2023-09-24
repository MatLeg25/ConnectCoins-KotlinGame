package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.connectcoins.ui.screens.ConfigScreen
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.screens.GameScreen
import com.example.connectcoins.ui.navigation.Screen
import com.example.connectcoins.ui.theme.ConnectCoinsTheme
import com.example.connectcoins.utils.Utils


class MainActivity : ComponentActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConnectCoinsTheme {

                var backgroundResId by remember {
                    mutableStateOf(Utils.getNextBackground())
                }
                gameViewModel = viewModel()

                Column {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .paint(
                                painter = painterResource(id = backgroundResId),
                                contentScale = ContentScale.Crop
                            )
                    ) {

                        //////////////////////// NAVIGATION
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = Screen.ConfigScreen.route) {
                            composable(route = Screen.GameScreen.route) {
                                GameScreen(gameViewModel, navController)
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
                                ConfigScreen(
                                    name = entry.arguments?.getString("name"),
                                    changeBackground = {
                                        backgroundResId = Utils.getNextBackground()
                                    },
                                    viewModel = gameViewModel,
                                    navController = navController
                                )
                            }
                        }
                        /////////////////////////////
                    }

                }


            }
        }
    }
}



