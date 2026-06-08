package ru.ifedorov.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme

private val LoadingStateIndicatorSize = 32.dp
private val LoadingStatePadding = 24.dp
private val LoadingStateSpacing = 12.dp
private val LoadingStateStrokeWidth = 4.dp

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LoadingStatePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LoadingStateSpacing)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(LoadingStateIndicatorSize),
            strokeWidth = LoadingStateStrokeWidth,
            color = MaterialTheme.colorScheme.primary
        )

        if (message != null) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingStatePreview() {
    KinopoiskAppTheme {
        LoadingState(message = "Загрузка")
    }
}
