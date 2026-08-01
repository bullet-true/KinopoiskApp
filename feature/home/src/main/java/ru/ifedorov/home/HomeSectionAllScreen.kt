package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.component.EmptyState
import ru.ifedorov.designsystem.component.ErrorState
import ru.ifedorov.designsystem.component.LoadingState
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.domain.model.FilmCollectionType
import ru.ifedorov.home.preview.PreviewHomeSection

private const val HOME_SECTION_ALL_GRID_COLUMN_COUNT = 2
private val HomeSectionAllHorizontalPadding = 26.dp
private val HomeSectionAllTopPadding = 24.dp
private val HomeSectionAllBottomPadding = 32.dp
private val HomeSectionAllTopBarHeight = 56.dp
private val HomeSectionAllContentTopSpacing = 32.dp
private val HomeSectionAllGridHorizontalSpacing = 2.dp
private val HomeSectionAllGridVerticalSpacing = 16.dp

@Composable
fun HomeSectionAllRoute(
    sectionTypeName: String?,
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sectionType = sectionTypeName.toFilmCollectionTypeOrNull()

    HomeSectionAllScreen(
        sectionState = uiState.sectionStateByType(sectionType),
        onRetryClick = { viewModel.onRetryClick() },
        onBackClick = onBackClick
    )
}

@Composable
internal fun HomeSectionAllScreen(
    sectionState: HomeSectionState?,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = HomeSectionAllHorizontalPadding,
                top = HomeSectionAllTopPadding,
                end = HomeSectionAllHorizontalPadding,
                bottom = HomeSectionAllBottomPadding
            ),
        horizontalAlignment = Alignment.Start
    ) {
        HomeSectionAllTopBar(
            title = sectionState.titleOrDefault(),
            onBackClick = onBackClick
        )

        when (sectionState) {
            null -> ErrorState(
                title = "Секция не найдена",
                message = "Вернитесь на главную и откройте подборку ещё раз.",
                onActionClick = onBackClick
            )

            is HomeSectionState.Loading -> LoadingState()
            is HomeSectionState.Empty -> EmptyState(title = "В этой секции пока пусто")
            is HomeSectionState.Error -> ErrorState(
                title = "Не удалось загрузить ${sectionState.title}",
                message = sectionState.message,
                onActionClick = onRetryClick
            )

            is HomeSectionState.Content -> HomeSectionAllContent(
                section = sectionState.section,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun HomeSectionAllContent(
    section: HomeSectionUiModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(HOME_SECTION_ALL_GRID_COLUMN_COUNT),
        horizontalArrangement = Arrangement.spacedBy(HomeSectionAllGridHorizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(HomeSectionAllGridVerticalSpacing),
        contentPadding = PaddingValues(
            top = HomeSectionAllContentTopSpacing,
            bottom = HomeSectionAllBottomPadding
        ),
        modifier = modifier
    ) {
        items(
            items = section.films,
            key = { film -> film.kinopoiskId }
        ) { film ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                HomeMovieCard(film = film)
            }
        }
    }
}

@Composable
private fun HomeSectionAllTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HomeSectionAllTopBarHeight),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

private fun HomeUiState.sectionStateByType(sectionType: FilmCollectionType?): HomeSectionState? =
    sections.firstOrNull { sectionState ->
        sectionState.typeOrNull == sectionType
    }

private val HomeSectionState.typeOrNull: FilmCollectionType?
    get() = when (this) {
        is HomeSectionState.Content -> section.type
        is HomeSectionState.Empty -> title.toSectionTypeOrNull()
        is HomeSectionState.Error -> title.toSectionTypeOrNull()
        is HomeSectionState.Loading -> title.toSectionTypeOrNull()
    }

private fun String?.toFilmCollectionTypeOrNull(): FilmCollectionType? =
    runCatching { FilmCollectionType.valueOf(orEmpty()) }.getOrNull()

private fun String.toSectionTypeOrNull(): FilmCollectionType? = when (this) {
    HOME_SECTION_TITLE_PREMIERES -> FilmCollectionType.PREMIERES
    HOME_SECTION_TITLE_POPULAR -> FilmCollectionType.POPULAR
    HOME_SECTION_TITLE_TOP_250 -> FilmCollectionType.TOP_250
    HOME_SECTION_TITLE_SERIES -> FilmCollectionType.SERIES
    else -> null
}

private fun HomeSectionState?.titleOrDefault(): String = when (this) {
    is HomeSectionState.Content -> section.title
    is HomeSectionState.Empty -> title
    is HomeSectionState.Error -> title
    is HomeSectionState.Loading -> title
    null -> "Все"
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionAllScreenPreview() {
    KinopoiskAppTheme {
        HomeSectionAllScreen(
            sectionState = HomeSectionState.Content(section = PreviewHomeSection),
            onRetryClick = {},
            onBackClick = {}
        )
    }
}
