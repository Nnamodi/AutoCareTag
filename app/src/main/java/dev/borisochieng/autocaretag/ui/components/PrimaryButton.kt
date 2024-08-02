package dev.borisochieng.autocaretag.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean
) {
    Button(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        shape = shape.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onBackground
        ),
        enabled = isEnabled
    ) {
        Text(
            text = label,
            style = typography.body,
            color = colorScheme.background,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun PrimaryOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean
) {
    OutlinedButton(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = label,
            style = typography.body,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonReview() {
    PrimaryButton(onClick = {}, label = "Update", isEnabled = false)
}