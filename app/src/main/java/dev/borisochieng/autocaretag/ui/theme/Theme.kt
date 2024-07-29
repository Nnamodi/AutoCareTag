package dev.borisochieng.autocaretag.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme =
    AutoCareColorScheme(
        primary = primary,
        secondary = secondary,
        background = background,
        onBackground = onBackground,
        container = container,
        onContainer = onContainer,
        onBackgroundVariant = onBackgroundVariant


    )

private val typography = AutoCareTypography(
    title = TextStyle(
        fontFamily = inter,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
    ),
    body = TextStyle(
        fontFamily = inter ,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
    ),
    bodyLight = TextStyle(
        fontFamily = inter,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        color = onBackgroundVariant
    ),
    bodyLarge = TextStyle(
        fontFamily = inter,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
    )

)

private val shape = AutoCareShape(
    container = RoundedCornerShape(6.dp),
    button = RoundedCornerShape(8.dp),
    card = RoundedCornerShape(8.dp),
)
@Composable
fun AutoCareTagTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider (
        LocalColorScheme provides LightColorScheme,
        LocalTypography provides typography,
        LocalShape provides shape,
        content = content
    )
}

object AutoCareTheme {
    val colorScheme: AutoCareColorScheme
    @Composable
    get() = LocalColorScheme.current
    val typography: AutoCareTypography
    @Composable
    get() = LocalTypography.current
    val shape: AutoCareShape
    @Composable
    get() = LocalShape.current
}