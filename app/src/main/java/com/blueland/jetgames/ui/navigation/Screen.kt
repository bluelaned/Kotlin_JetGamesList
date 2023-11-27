package com.blueland.jetgames.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailGame : Screen("home/{gameId}") {
        fun createRoute(gameId: String) = "home/$gameId"
    }
    object Profile : Screen("profile")
    object Favorite : Screen("favorite")
}