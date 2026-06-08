package ru.ifedorov.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.designsystem.R
import ru.ifedorov.designsystem.theme.KinopoiskAppTheme

private val ShowAllItemWidth = 111.dp
private val ShowAllItemPosterHeight = 156.dp
private val ShowAllItemIconSize = 32.dp
private val ShowAllItemContentSpacing = 8.dp

@Composable
fun ShowAllItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Показать все"
) {
    Box(
        modifier = modifier
            .size(
                width = ShowAllItemWidth,
                height = ShowAllItemPosterHeight
            )
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ShowAllItemContentSpacing)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_show_all),
                contentDescription = null,
                modifier = Modifier.size(ShowAllItemIconSize)
            )

            Text(
                text = text,
                modifier = Modifier.width(ShowAllItemWidth),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowAllItemPreview() {
    KinopoiskAppTheme {
        ShowAllItem(onClick = {})
    }
}
