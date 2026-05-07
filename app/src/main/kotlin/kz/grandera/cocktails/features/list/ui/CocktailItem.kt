package kz.grandera.cocktails.features.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import coil3.compose.AsyncImage

import kz.grandera.cocktails.domain.cocktail.Cocktail

@Composable
internal fun CocktailItem(
    cocktail: Cocktail,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.large)
            .background(color = MaterialTheme.colors.surface),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = cocktail.imageUrl,
            contentDescription = cocktail.name,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.22f)),
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(all = 12.dp),
            text = cocktail.name,
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
            ),
        )
    }
}
