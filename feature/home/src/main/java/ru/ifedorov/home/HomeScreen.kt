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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ifedorov.designsystem.component.EmptyState
import ru.ifedorov.designsystem.component.ErrorState
import ru.ifedorov.designsystem.component.LoadingState
import ru.ifedorov.designsystem.component.SectionHeader

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

/** Отступ состояния секции от её заголовка */
private val HomeSectionStateTopPadding = 12.dp

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
            text = "Kinopoisk",
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
    HomeSectionCarousel(
        section = section,
        onShowAllClick = onShowAllClick
    )
}
