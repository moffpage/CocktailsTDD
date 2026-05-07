package kz.grandera.cocktails.features.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .semantics { contentDescription = "Loading cocktails" }
            .background(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
            .fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.primary,
        )
    }
}

@Composable
internal fun ErrorContent(
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Could not load cocktails",
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                ),
            )
            Text(
                text = "Check the connection and try again",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                ),
            )
            Button(onClick = onRetryClicked) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
internal fun EmptyContent() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        text = "No cocktails found",
        style = MaterialTheme.typography.h4.copy(
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        ),
    )
}
