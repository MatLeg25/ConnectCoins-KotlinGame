package com.example.connectcoins.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(gameViewModel: GameViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            TopBar(navController)
        }
        composable(
            route = Screen.ConfigScreen.route + "/{name}", //"?name={name}",
            arguments = listOf(
                navArgument("name") {
                type = NavType.StringType
                defaultValue = "Player :D"
                nullable = true
                }
            )
        ) { entry ->
            ConfigScreen(name = entry.arguments?.getString("name"), gameViewModel)
        }
    }
}