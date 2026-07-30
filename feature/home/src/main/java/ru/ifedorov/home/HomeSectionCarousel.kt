package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.component.MovieCard
import ru.ifedorov.designsystem.component.ShowAllItem
import ru.ifedorov.domain.model.Film

/** Расстояние между карточками в горизонтальном списке по макету. */
private val HomeCarouselItemSpacing = 8.dp

private const val HOME_CAROUSEL_PREVIEW_LIMIT = 8

@Composable
internal fun HomeSectionCarousel(
    section: HomeSectionUiModel,
    onShowAllClick: (HomeSectionUiModel) -> Unit
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
            HomeMovieCard(film = film)
        }

        item(key = "${section.type}-show-all") {
            ShowAllItem(onClick = { onShowAllClick(section) })
        }
    }
}

@Composable
private fun HomeMovieCard(film: Film) {
    val posterUrl = film.posterUrlPreview ?: film.posterUrl

    MovieCard(
        title = film.title,
        genre = film.genres.firstOrNull().orEmpty(),
        rating = film.rating?.toString(),
        isWatched = film.isWatched,
        posterContent = posterUrl?.let { imageUrl ->
            {
                HomeMoviePoster(
                    imageUrl = imageUrl,
                    title = film.title,
                    isWatched = film.isWatched
                )
            }
        }
    )
}

@Composable
private fun HomeMoviePoster(
    imageUrl: String,
    title: String,
    isWatched: Boolean
) {
    val placeholderResId = if (isWatched) {
        R.drawable.placeholder_movie_watched
    } else {
        R.drawable.placeholder_movie_not_watched
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        placeholder = painterResource(placeholderResId),
        error = painterResource(placeholderResId),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}
