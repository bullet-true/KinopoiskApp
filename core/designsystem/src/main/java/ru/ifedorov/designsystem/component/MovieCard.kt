package ru.ifedorov.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme

private val MovieCardWidth = 150.dp
private val MovieCardHeight = 250.dp
private val MovieCardPosterHeight = 200.dp
private val MovieCardPosterCornerRadius = 4.dp
private val MovieCardContentSpacing = 8.dp
private val MovieCardTextHeight = 60.dp
private val MovieCardRatingPadding = 4.dp
private val MovieCardWatchedIconPadding = 8.dp
private val MovieCardWatchedIconSize = 18.dp
private val MovieCardRatingHorizontalPadding = 4.dp
private val MovieCardRatingVerticalPadding = 2.dp
private val MovieCardRatingFontSize = 8.sp
private val MovieCardRatingLineHeight = 8.sp
private const val MOVIE_CARD_WATCHED_OVERLAY_ALPHA = 0.4f

@Composable
fun MovieCard(
    title: String,
    genre: String,
    modifier: Modifier = Modifier,
    rating: String? = null,
    isWatched: Boolean = false,
    onClick: (() -> Unit)? = null,
    posterContent: (@Composable BoxScope.() -> Unit)? = null
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable(
            role = Role.Button,
            onClick = onClick
        )
    } else {
        modifier
    }

    Column(
        modifier = cardModifier
            .width(MovieCardWidth)
            .height(MovieCardHeight),
        verticalArrangement = Arrangement.spacedBy(MovieCardContentSpacing)
    ) {
        MoviePoster(
            rating = rating,
            isWatched = isWatched,
            posterContent = posterContent
        )

        MovieCardTitle(
            title = title,
            genre = genre,
            modifier = Modifier.height(MovieCardTextHeight)
        )
    }
}

@Composable
private fun MoviePoster(
    rating: String?,
    isWatched: Boolean,
    posterContent: (@Composable BoxScope.() -> Unit)?
) {
    Box(
        modifier = Modifier
            .size(
                width = MovieCardWidth,
                height = MovieCardPosterHeight
            )
            .clip(RoundedCornerShape(MovieCardPosterCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        if (posterContent != null) {
            posterContent()
        } else {
            MoviePosterPlaceholder(isWatched = isWatched)
        }

        if (isWatched && posterContent != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = MOVIE_CARD_WATCHED_OVERLAY_ALPHA
                        )
                    )
            )
        }

        if (isWatched) {
            Image(
                painter = painterResource(R.drawable.ic_movie_card_watched),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MovieCardWatchedIconPadding)
                    .size(MovieCardWatchedIconSize)
            )
        }

        if (rating != null) {
            MovieRatingBadge(
                rating = rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(MovieCardRatingPadding)
            )
        }
    }
}

@Composable
private fun MoviePosterPlaceholder(isWatched: Boolean) {
    val placeholderResId = if (isWatched) {
        R.drawable.placeholder_movie_watched
    } else {
        R.drawable.placeholder_movie_not_watched
    }

    Image(
        painter = painterResource(placeholderResId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun MovieRatingBadge(
    rating: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = MovieCardRatingHorizontalPadding,
                vertical = MovieCardRatingVerticalPadding
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rating,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = MovieCardRatingFontSize,
                lineHeight = MovieCardRatingLineHeight
            )
        )
    }
}

@Composable
private fun MovieCardTitle(
    title: String,
    genre: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = genre,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    KinopoiskAppTheme {
        MovieCard(
            title = "Doom",
            genre = "фантастика",
            rating = "7.8",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchedMovieCardPreview() {
    KinopoiskAppTheme {
        MovieCard(
            title = "Дюна",
            genre = "фантастика",
            rating = "8.8",
            isWatched = true,
            onClick = {}
        )
    }
}
