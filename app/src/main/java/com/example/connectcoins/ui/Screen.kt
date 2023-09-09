package com.example.connectcoins.ui

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object ConfigScreen: Screen("config_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
