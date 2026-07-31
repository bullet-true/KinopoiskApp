package ru.ifedorov.kinopoiskapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.ifedorov.home.HomeRoute
import ru.ifedorov.home.HomeSectionAllRoute
import ru.ifedorov.navigation.AppDestination
import ru.ifedorov.onboarding.OnboardingRoute
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
fun KinopoiskApp(viewModel: KinopoiskAppViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        KinopoiskAppUiState.Loading -> KinopoiskStartupLoading()
        KinopoiskAppUiState.Main -> KinopoiskAppContent(startDestination = AppDestination.Home.route)
        KinopoiskAppUiState.Onboarding -> KinopoiskAppContent(startDestination = AppDestination.Onboarding.route)
    }
}

@Composable
private fun KinopoiskAppContent(startDestination: String) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentRoute = currentDestination?.route
    val shouldShowBottomBar =
        currentRoute != null && currentRoute != AppDestination.Onboarding.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (shouldShowBottomBar) {
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestination.Onboarding.route) {
                OnboardingRoute(
                    onOnboardingFinished = {
                        navController.navigate(AppDestination.Home.route) {
                            popUpTo(AppDestination.Onboarding.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(AppDestination.Home.route) {
                HomeRoute(
                    onShowAllClick = { section ->
                        navController.navigate(AppDestination.HomeSectionAll.createRoute(section.type.name))
                    }
                )
            }

            composable(
                route = AppDestination.HomeSectionAll.route,
                arguments = listOf(navArgument(AppDestination.SECTION_TYPE_ARGUMENT) {})
            ) { backStackEntry ->
                HomeSectionAllRoute(
                    sectionTypeName = backStackEntry.arguments?.getString(AppDestination.SECTION_TYPE_ARGUMENT),
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
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

@Composable
private fun KinopoiskStartupLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}
