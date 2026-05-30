package ru.ifedorov.navigation

sealed class AppDestination(val route: String) {
    data object Onboarding : AppDestination("onboarding")
    data object Home : AppDestination("home")
    data object Search : AppDestination("search")
    data object Profile : AppDestination("profile")
    data object FilmDetails : AppDestination("film/{filmId}") {
        fun createRoute(filmId: Int): String = "film/$filmId"
    }
}
