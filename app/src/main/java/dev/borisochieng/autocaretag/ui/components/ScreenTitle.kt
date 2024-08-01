package dev.borisochieng.autocaretag.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier
) {
    val title = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorScheme.onBackground
            )
        ) {
            append(stringResource(R.string.auto))
        }
        withStyle(
            style = SpanStyle(
                color = colorScheme.primary
            )
        ) {
            append(stringResource(R.string.care))
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = typography.titleLarge,
            textAlign = TextAlign.Center,
        )


    }

}