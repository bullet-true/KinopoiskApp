package ru.ifedorov.kinopoiskapp.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ifedorov.home.HomeScreen
import ru.ifedorov.navigation.AppDestination
import ru.ifedorov.profile.ProfileScreen
import ru.ifedorov.search.SearchScreen
import ru.ifedorov.designsystem.R as DesignSystemR

private val topLevelDestinations = listOf(
    AppTopLevelDestination(
        destination = AppDestination.Home,
        label = "Главная",
        selectedIconResId = DesignSystemR.drawable.ic_home_selected,
        unselectedIconResId = DesignSystemR.drawable.ic_home_unselected
    ),
    AppTopLevelDestination(
        destination = AppDestination.Search,
        label = "Поиск",
        selectedIconResId = DesignSystemR.drawable.ic_search_selected,
        unselectedIconResId = DesignSystemR.drawable.ic_search_unselected
    ),
    AppTopLevelDestination(
        destination = AppDestination.Profile,
        label = "Профиль",
        selectedIconResId = DesignSystemR.drawable.ic_profile_selected,
        unselectedIconResId = DesignSystemR.drawable.ic_profile_unselected
    )
)

@Composable
fun KinopoiskApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            KinopoiskBottomBar(
                topLevelDestinations = topLevelDestinations,
                currentDestination = currentDestination,
                onDestinationClick = { item ->
                    navController.navigate(item.destination.route) {
                        popUpTo(AppDestination.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
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
