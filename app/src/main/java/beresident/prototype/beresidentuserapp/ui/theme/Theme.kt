package beresident.prototype.beresidentuserapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private  val LocalAppDimens = staticCompositionLocalOf {
    smallDimensions
}

private val DarkColorPalette = darkColors(
    primary = Color(12,40,54),
    primaryVariant = Color(32, 33, 36, 255),
    secondary = Color(53,176,130),
    surface = Color(48,49,52, 255)
)

private val LightColorPalette = lightColors(
    primary = Color(12,40,54),
    primaryVariant = Color(255,255,255),
    secondary = Color(53,176,130),
    surface = Color(240,240,240, 255)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val dimensions = if (configuration.screenWidthDp <= 360) smallDimensions else sw360Dimensions

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}

object DefaultTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}

val Dimens : Dimensions
    @Composable
    get() = DefaultTheme.dimens