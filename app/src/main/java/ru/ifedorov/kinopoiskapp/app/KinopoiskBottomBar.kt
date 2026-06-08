package ru.ifedorov.kinopoiskapp.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.navigation.AppDestination
import ru.ifedorov.designsystem.R as DesignSystemR

private val BottomBarHeight = 64.dp
private val BottomBarTopCornerRadius = 16.dp
private val BottomBarIconSize = 32.dp
private val BottomBarItemSize = 60.dp
private val BottomBarShadowElevation = 8.dp
private val BottomBarVisibleIconSpacing = 60.dp
private val BottomBarItemSpacing =
    BottomBarVisibleIconSpacing - (BottomBarItemSize - BottomBarIconSize)

@Composable
internal fun KinopoiskBottomBar(
    topLevelDestinations: List<AppTopLevelDestination>,
    currentDestination: NavDestination?,
    onDestinationClick: (AppTopLevelDestination) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(BottomBarHeight),
        shape = RoundedCornerShape(
            topStart = BottomBarTopCornerRadius,
            topEnd = BottomBarTopCornerRadius
        ),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = BottomBarShadowElevation
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                space = BottomBarItemSpacing,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            topLevelDestinations.forEach { item ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == item.destination.route
                } == true

                IconButton(
                    modifier = Modifier.size(BottomBarItemSize),
                    onClick = { onDestinationClick(item) }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (selected) {
                                item.selectedIconResId
                            } else {
                                item.unselectedIconResId
                            }
                        ),
                        contentDescription = item.label,
                        modifier = Modifier.size(BottomBarIconSize),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KinopoiskBottomBarPreview() {
    val previewDestinations = listOf(
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
    val previewDestination = NavDestination("preview").apply {
        route = AppDestination.Home.route
    }

    KinopoiskAppTheme {
        KinopoiskBottomBar(
            topLevelDestinations = previewDestinations,
            currentDestination = previewDestination,
            onDestinationClick = {}
        )
    }
}
