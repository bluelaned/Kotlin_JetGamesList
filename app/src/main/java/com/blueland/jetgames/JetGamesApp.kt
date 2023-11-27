package com.blueland.jetgames


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blueland.jetgames.ui.theme.JetGamesTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import com.blueland.jetgames.ui.navigation.NavigationItem
import com.blueland.jetgames.ui.navigation.Screen
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blueland.jetgames.ui.screen.profile.ProfileScreen
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.blueland.jetgames.ui.screen.HomeScreen
import com.blueland.jetgames.ui.screen.detail.DetailScreen
import com.blueland.jetgames.ui.screen.favorite.FavoriteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetGamesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailGame.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { gameId ->
                        navController.navigate(Screen.DetailGame.createRoute(gameId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailGame.route,
                arguments = listOf(navArgument("gameId") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("gameId")
                DetailScreen(
                    gameId = id ?: "",
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.Favorite.route
            ) {
                FavoriteScreen(
                    navigateBack = { navController.navigateUp() },
                    navigateToDetail = { gameId ->
                        navController.navigate(Screen.DetailGame.createRoute(gameId))
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun JetGamesAppPreview() {
    JetGamesTheme {
        JetGamesApp()
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
            NavigationItem(
                title = "Favorite",
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}