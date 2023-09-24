package com.example.connectcoins.ui.navigation

sealed class Screen(val route: String) {
    object GameScreen: Screen("game_screen")
    object ConfigScreen: Screen("config_screen")

    //handle not-optional args
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
