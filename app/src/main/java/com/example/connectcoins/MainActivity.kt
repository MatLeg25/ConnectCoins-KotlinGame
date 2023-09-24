package com.example.connectcoins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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



