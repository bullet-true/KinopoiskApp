package ru.ifedorov.kinopoiskapp.app

import androidx.annotation.DrawableRes
import ru.ifedorov.navigation.AppDestination

internal data class AppTopLevelDestination(
    val destination: AppDestination,
    val label: String,
    @param:DrawableRes val selectedIconResId: Int,
    @param:DrawableRes val unselectedIconResId: Int
)
