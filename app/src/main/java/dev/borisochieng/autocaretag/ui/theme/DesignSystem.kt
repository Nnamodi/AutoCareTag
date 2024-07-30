package dev.borisochieng.autocaretag.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

data class AutoCareColorScheme(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val onBackground: Color,
    val container: Color,
    val onContainer: Color,
    val onBackgroundVariant: Color,
    val error: Color
)

data class AutoCareTypography(
    val title: TextStyle,
    val body: TextStyle,
    val bodyLight: TextStyle,
    val bodyLarge: TextStyle
)

data class AutoCareShape(
    val container: Shape,
    val button: Shape,
    val card: Shape,
)

val LocalColorScheme = staticCompositionLocalOf {
    AutoCareColorScheme(
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        container = Color.Unspecified,
        onContainer = Color.Unspecified,
        onBackgroundVariant = Color.Unspecified,
        error = Color.Unspecified
    )
}

val LocalTypography = staticCompositionLocalOf {
    AutoCareTypography(
        title = TextStyle.Default,
        body = TextStyle.Default,
        bodyLight = TextStyle.Default,
        bodyLarge = TextStyle.Default
    )
}

val LocalShape = staticCompositionLocalOf {
    AutoCareShape(
        container = RectangleShape,
        button = RectangleShape,
        card = RectangleShape
    )
}



