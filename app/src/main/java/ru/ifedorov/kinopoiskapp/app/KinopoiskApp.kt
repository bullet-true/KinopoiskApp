package ru.ifedorov.kinopoiskapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ifedorov.home.HomeScreen
import ru.ifedorov.navigation.AppDestination
import ru.ifedorov.navigation.TopLevelDestination
import ru.ifedorov.profile.ProfileScreen
import ru.ifedorov.search.SearchScreen

private val topLevelDestinations = listOf(
    TopLevelDestination(AppDestination.Home, "Главная"),
    TopLevelDestination(AppDestination.Search, "Поиск"),
    TopLevelDestination(AppDestination.Profile, "Профиль")
)

@Composable
fun KinopoiskApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar() {
                topLevelDestinations.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == item.destination.route
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.destination.route) {
                                popUpTo(AppDestination.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Box(modifier = Modifier) },
                        label = { Text(text = item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestination.Home.route) {
                HomeScreen()
            }
            composable(AppDestination.Search.route) {
                SearchScreen()
            }
            composable(AppDestination.Profile.route) {
                ProfileScreen()
            }
        }
    }
}