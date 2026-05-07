package kz.grandera.cocktails.features.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun SegmentedControl(
    selected: (index: Int) -> Boolean,
    titles: List<String>,
    onSegmentClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 2.dp)
                .clip(shape = CircleShape),
        ) {
            titles.forEachIndexed { index, title ->
                val isSelected = selected(index)
                val tabBackgroundColor = if (isSelected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }

                Surface(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .clip(shape = CircleShape)
                        .clickable { onSegmentClick(index) },
                    color = tabBackgroundColor,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = title,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
