package ru.ifedorov.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme

private val SectionHeaderHeight = 20.dp
private val SectionHeaderActionMinWidth = 48.dp
private val SectionHeaderPreviewPadding = 16.dp

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String = "Все",
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SectionHeaderHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (onActionClick != null) {
            val actionInteractionSource = remember { MutableInteractionSource() }

            Box(
                modifier = Modifier
                    .height(SectionHeaderHeight)
                    .sizeIn(
                        minWidth = SectionHeaderActionMinWidth
                    )
                    .clickable(
                        interactionSource = actionInteractionSource,
                        indication = null,
                        role = Role.Button,
                        onClick = onActionClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    KinopoiskAppTheme {
        SectionHeader(
            title = "Премьеры",
            modifier = Modifier.padding(SectionHeaderPreviewPadding),
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderWithoutActionPreview() {
    KinopoiskAppTheme {
        SectionHeader(
            title = "Похожие фильмы",
            modifier = Modifier.padding(SectionHeaderPreviewPadding)
        )
    }
}
