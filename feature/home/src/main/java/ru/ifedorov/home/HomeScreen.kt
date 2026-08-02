package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import ru.ifedorov.designsystem.component.SectionHeader
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.domain.model.Film
import ru.ifedorov.home.preview.PreviewHomeUiState

private val HomeHorizontalPadding = 26.dp
private val HomeTopPadding = 56.dp
private val HomeBottomPadding = 32.dp
private val HomeHeaderBottomSpacing = 20.dp
private val HomeSectionSpacing = 32.dp
private val HomeSectionStateTopPadding = 12.dp

@Composable
fun HomeRoute(
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRetryClick = { viewModel.onRetryClick() },
        onShowAllClick = onShowAllClick,
        onFilmClick = onFilmClick
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit
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
            text = "Kinopoisk",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = HomeHeaderBottomSpacing)
        )

        HomeSectionsContent(
            sections = uiState.sections,
            onRetryClick = onRetryClick,
            onShowAllClick = onShowAllClick,
            onFilmClick = onFilmClick
        )
    }
}

@Composable
private fun HomeSectionsContent(
    sections: List<HomeSectionState>,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit
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
                onShowAllClick = onShowAllClick,
                onFilmClick = onFilmClick
            )
        }
    }
}

@Composable
private fun HomeSectionSummary(
    sectionState: HomeSectionState,
    onRetryClick: () -> Unit,
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit
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
                    onShowAllClick = onShowAllClick,
                    onFilmClick = onFilmClick
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
    onShowAllClick: (HomeSectionUiModel) -> Unit,
    onFilmClick: (Film) -> Unit
) {
    HomeSectionCarousel(
        section = section,
        onShowAllClick = onShowAllClick,
        onFilmClick = onFilmClick
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    KinopoiskAppTheme {
        HomeScreen(
            uiState = PreviewHomeUiState,
            onRetryClick = {},
            onShowAllClick = {},
            onFilmClick = {}
        )
    }
}
