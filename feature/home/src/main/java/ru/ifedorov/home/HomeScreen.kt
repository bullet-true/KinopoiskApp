package ru.ifedorov.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

/** Горизонтальные отступы главного экрана по макету. */
private val HomeHorizontalPadding = 26.dp

/** Верхний отступ контента главной от края экрана. */
private val HomeTopPadding = 56.dp

/** Нижний отступ контента с учётом bottom bar. */
private val HomeBottomPadding = 32.dp

/** Расстояние между заголовком приложения и первой секцией. */
private val HomeHeaderBottomSpacing = 48.dp

/** Вертикальный промежуток между секциями главной. */
private val HomeSectionSpacing = 32.dp

/** Отступ summary-текста от заголовка секции. */
private val HomeSectionSummaryTopPadding = 8.dp

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRetryClick = { viewModel.onRetryClick() }
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetryClick: () -> Unit
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

        when (uiState) {
            HomeUiState.Loading -> HomeLoadingContent()
            is HomeUiState.Content -> HomeSectionsContent(sections = uiState.sections)
            is HomeUiState.Error -> HomeErrorContent(
                message = uiState.message,
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun HomeLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingState(message = "Загружаем подборки")
    }
}

@Composable
private fun HomeErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    ErrorState(
        message = message,
        onActionClick = onRetryClick
    )
}

@Composable
private fun HomeSectionsContent(sections: List<HomeSectionUiModel>) {
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
            HomeSectionSummary(section = section)
        }
    }
}

@Composable
private fun HomeSectionSummary(section: HomeSectionUiModel) {
    Column {
        SectionHeader(title = section.title)

        Text(
            text = "Фильмов в подборке: ${section.filmsCount}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = HomeSectionSummaryTopPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(
        HomeUiState.Loading,
        onRetryClick = {}
    )
}
