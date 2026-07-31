package ru.ifedorov.navigation

sealed class AppDestination(val route: String) {
    data object Onboarding : AppDestination("onboarding")

    data object Home : AppDestination("home")

    data object HomeSectionAll : AppDestination("home/section/{$SECTION_TYPE_ARGUMENT}") {
        fun createRoute(sectionType: String): String = "home/section/$sectionType"
    }

    data object Search : AppDestination("search")
    data object Profile : AppDestination("profile")
    data object FilmDetails : AppDestination("film/{filmId}") {
        fun createRoute(filmId: Int): String = "film/$filmId"
    }

    companion object {
        const val SECTION_TYPE_ARGUMENT = "sectionType"
    }
}
