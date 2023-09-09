package com.example.connectcoins

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
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
            ConfigScreen(name = entry.arguments?.getString("name"))
        }
    }
}

@Composable
fun ConfigScreen(name: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "GHello, $name")
    }
}