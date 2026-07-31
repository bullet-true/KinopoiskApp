package ru.ifedorov.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.component.MovieCard
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.domain.model.Film
import ru.ifedorov.home.preview.PreviewHomeFilms

@Composable
internal fun HomeMovieCard(film: Film) {
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

@Preview(showBackground = true)
@Composable
private fun HomeMovieCardPreview() {
    KinopoiskAppTheme {
        HomeMovieCard(film = PreviewHomeFilms.first())
    }
}
