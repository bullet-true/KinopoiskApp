package ru.ifedorov.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme

private val ErrorStatePadding = 24.dp
private val ErrorStateSpacing = 12.dp

@Composable
fun ErrorState(
    message: String,
    modifier: Modifier = Modifier,
    title: String = "Что-то пошло не так",
    actionText: String = "Повторить",
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ErrorStatePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ErrorStateSpacing)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        if (onActionClick != null) {
            Button(onClick = onActionClick) {
                Text(text = actionText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    KinopoiskAppTheme {
        ErrorState(
            message = "Не удалось загрузить данные",
            onActionClick = {}
        )
    }
}
