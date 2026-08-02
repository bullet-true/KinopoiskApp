package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.designsystem.component.ShowAllItem
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.domain.model.Film
import ru.ifedorov.home.preview.PreviewHomeSection

private val HomeCarouselItemSpacing = 8.dp

private const val HOME_CAROUSEL_PREVIEW_LIMIT = 8

@Composable
internal fun HomeSectionCarousel(
    section: HomeSectionUiModel,
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit
) {
    val visibleFilms = section.films.take(HOME_CAROUSEL_PREVIEW_LIMIT)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(HomeCarouselItemSpacing),
        contentPadding = PaddingValues(),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = visibleFilms,
            key = { film -> film.kinopoiskId }
        ) { film ->
            HomeMovieCard(
                film = film,
                onClick = onFilmClick
            )
        }

        item(key = "${section.type}-show-all") {
            ShowAllItem(onClick = { onShowAllClick(section) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionCarouselPreview() {
    KinopoiskAppTheme {
        HomeSectionCarousel(
            section = PreviewHomeSection,
            onShowAllClick = {},
            onFilmClick = {}
        )
    }
}
