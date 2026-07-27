package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.component.EmptyState
import ru.ifedorov.designsystem.component.ErrorState
import ru.ifedorov.designsystem.component.LoadingState
import ru.ifedorov.designsystem.component.MovieCard
import ru.ifedorov.designsystem.component.SectionHeader
import ru.ifedorov.designsystem.component.ShowAllItem
import ru.ifedorov.domain.model.Film
import ru.ifedorov.domain.model.FilmCollectionType

/** Горизонтальные отступы главного экрана */
private val HomeHorizontalPadding = 26.dp

/** Верхний отступ контента главной от края экрана */
private val HomeTopPadding = 56.dp

/** Нижний отступ контента с учётом bottom bar */
private val HomeBottomPadding = 32.dp

/** Расстояние между заголовком приложения и первой секцией */
private val HomeHeaderBottomSpacing = 48.dp

/** Вертикальный промежуток между секциями главной */
private val HomeSectionSpacing = 32.dp

/** Отступ summary-текста от заголовка секции */
private val HomeSectionSummaryTopPadding = 8.dp

/** Отступ состояния секции от её заголовка */
private val HomeSectionStateTopPadding = 12.dp

/** Расстояние между карточками в горизонтальном списке */
private val HomeCarouselItemSpacing = 8.dp

private const val HOME_CAROUSEL_PREVIEW_LIMIT = 8

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRetryClick = { viewModel.onRetryClick() },
        onShowAllClick = {}
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = HomeHorizontalPadding,
                top = HomeTopPadding,
                end = HomeHorizontalPadding,
                bottom = HomeBottomPadding
            ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Skillcinema",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = HomeHeaderBottomSpacing)
        )

        HomeSectionsContent(
            sections = uiState.sections,
            onRetryClick = onRetryClick,
            onShowAllClick = onShowAllClick
        )
    }
}

@Composable
private fun HomeSectionsContent(
    sections: List<HomeSectionState>,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit
) {
    if (sections.isEmpty()) {
        EmptyState(
            title = "Подборки не найдены",
            message = "Попробуйте обновить главную позже."
        )
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(HomeSectionSpacing)
    ) {
        sections.forEach { section ->
            HomeSectionSummary(
                sectionState = section,
                onRetryClick = onRetryClick,
                onShowAllClick = onShowAllClick
            )
        }
    }
}

@Composable
private fun HomeSectionSummary(
    sectionState: HomeSectionState,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit
) {
    val title = when (sectionState) {
        is HomeSectionState.Loading -> sectionState.title
        is HomeSectionState.Content -> sectionState.section.title
        is HomeSectionState.Empty -> sectionState.title
        is HomeSectionState.Error -> sectionState.title
    }

    Column {
        SectionHeader(title = title)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = HomeSectionStateTopPadding),
            contentAlignment = Alignment.CenterStart
        ) {
            when (sectionState) {
                is HomeSectionState.Loading -> LoadingState()
                is HomeSectionState.Content -> HomeSectionContent(
                    section = sectionState.section,
                    onShowAllClick = onShowAllClick
                )

                is HomeSectionState.Empty -> EmptyState(title = "В этой секции пока пусто")
                is HomeSectionState.Error -> ErrorState(
                    title = "Не удалось загрузить ${sectionState.title}",
                    message = sectionState.message,
                    onActionClick = onRetryClick
                )
            }
        }
    }
}

@Composable
private fun HomeSectionContent(
    section: HomeSectionUiModel,
    onShowAllClick: (HomeSectionUiModel) -> Unit
) {
    if (section.type == FilmCollectionType.PREMIERES) {
        PremieresCarousel(
            section = section,
            onShowAllClick = onShowAllClick
        )
    } else {
        Text(
            text = "Фильмов в подборке: ${section.filmsCount}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = HomeSectionSummaryTopPadding)
        )
    }
}

@Composable
private fun PremieresCarousel(
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
