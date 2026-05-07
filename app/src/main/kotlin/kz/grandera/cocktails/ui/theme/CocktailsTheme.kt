package kz.grandera.cocktails.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kz.grandera.cocktails.R

@Composable
fun CocktailsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightCocktailsColors,
        shapes = cocktailsShapes,
        typography = cocktailsTypography,
    ) {
        Surface(content = content)
    }
}

private val lightCocktailsColors: Colors = lightColors(
    primary = Color(0xFFECBE43),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF212121),
    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF9E9E9E),
    secondary = Color(0xFF4469EB),
    error = Color(0xFFB3261E),
)

private val cocktailsShapes: Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
)

private val aliceFontFamily = FontFamily(
    Font(
        resId = R.font.alice_regular,
        weight = FontWeight.Normal,
    ),
)

private val cocktailsTypography: Typography = Typography(
    h1 = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = aliceFontFamily,
        lineHeight = 34.sp,
    ),
    h4 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = aliceFontFamily,
        lineHeight = 18.sp,
    ),
    body1 = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = aliceFontFamily,
        lineHeight = 16.sp,
    ),
)
