package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ifedorov.designsystem.component.EmptyState
import ru.ifedorov.designsystem.component.ErrorState
import ru.ifedorov.designsystem.component.LoadingState
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.domain.model.FilmCollectionType
import ru.ifedorov.home.preview.PreviewHomeSection

private const val GRID_SIZE = 2
private val HomeSectionAllHorizontalPadding = 26.dp
private val HomeSectionAllTopPadding = 32.dp
private val HomeSectionAllBottomPadding = 32.dp
private val HomeSectionAllHeaderSpacing = 24.dp
private val HomeSectionAllGridHorizontalSpacing = 8.dp
private val HomeSectionAllGridVerticalSpacing = 24.dp

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
        TextButton(onClick = onBackClick) {
            Text(
                text = "Назад",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        when (sectionState) {
            null -> ErrorState(
                title = "Секция не найдена",
                message = "Вернитесь на главную и откройте подборку ещё раз.",
                onActionClick = onBackClick
            )

            is HomeSectionState.Loading -> LoadingState(message = "Загружаем секцию")
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
    Text(
        text = section.title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(
            top = HomeSectionAllHeaderSpacing,
            bottom = HomeSectionAllHeaderSpacing
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_SIZE),
        horizontalArrangement = Arrangement.spacedBy(HomeSectionAllGridHorizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(HomeSectionAllGridVerticalSpacing),
        contentPadding = PaddingValues(bottom = HomeSectionAllBottomPadding),
        modifier = modifier
    ) {
        items(
            items = section.films,
            key = { film -> film.kinopoiskId }
        ) { film ->
            HomeMovieCard(film = film)
        }
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
