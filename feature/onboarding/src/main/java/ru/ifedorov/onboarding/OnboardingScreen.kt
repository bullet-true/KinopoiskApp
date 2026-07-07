package ru.ifedorov.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme
import ru.ifedorov.designsystem.theme.TextSecondaryColor

private val OnboardingHorizontalPadding = 26.dp
private val OnboardingTopPadding = 38.dp
private val OnboardingTitleBottomPadding = 56.dp
private val OnboardingTitleStartPadding = 26.dp
private val OnboardingDotsBottomPadding = 46.dp
private val OnboardingDotSize = 8.dp
private val OnboardingDotSpacing = 4.dp

@Composable
fun OnboardingRoute(
    onOnboardingFinished: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.finishEvents.collect {
            onOnboardingFinished()
        }
    }

    OnboardingScreen(
        onFinishClick = viewModel::onFinishClick,
        isFinishActionEnabled = !uiState.isCompleting,
    )
}

@Composable
fun OnboardingScreen(
    onFinishClick: () -> Unit,
    isFinishActionEnabled: Boolean = true
) {
    val pages = remember { onboardingPages }
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        OnboardingHeader(
            isSkipEnabled = isFinishActionEnabled,
            onSkipClick = onFinishClick
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            OnboardingPageContent(page = pages[pageIndex])
        }

        OnboardingDotsIndicator(
            pageCount = pages.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = OnboardingHorizontalPadding,
                    bottom = OnboardingDotsBottomPadding
                )
        )
    }
}

@Composable
private fun OnboardingHeader(
    isSkipEnabled: Boolean,
    onSkipClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = OnboardingHorizontalPadding,
                top = OnboardingTopPadding,
                end = OnboardingHorizontalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Kinopoisk",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Пропустить",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryColor,
            modifier = Modifier.clickable(
                enabled = isSkipEnabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSkipClick
            )
        )
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(2f))

        Image(
            painter = painterResource(id = page.imageResId),
            contentDescription = page.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(2f))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                start = OnboardingTitleStartPadding,
                bottom = OnboardingTitleBottomPadding
            )
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun OnboardingDotsIndicator(
    pageCount: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(OnboardingDotSpacing)
    ) {
        repeat(pageCount) { index ->
            val dotColor = if (index == selectedPage) {
                MaterialTheme.colorScheme.onBackground
            } else {
                TextSecondaryColor.copy(alpha = 0.7f)
            }

            Box(
                modifier = Modifier
                    .size(OnboardingDotSize)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
    }
}

private data class OnboardingPage(
    val title: String,
    @param:DrawableRes val imageResId: Int
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Узнавай\nо премьерах",
        imageResId = R.drawable.logo_onboarding1
    ),
    OnboardingPage(
        title = "Создавай\nколлекции",
        imageResId = R.drawable.logo_onboarding2
    ),
    OnboardingPage(
        title = "Делись\nс друзьями",
        imageResId = R.drawable.logo_onboarding3
    )
)

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview() {
    KinopoiskAppTheme {
        OnboardingScreen(
            onFinishClick = {}
        )
    }
}
