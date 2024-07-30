package dev.borisochieng.autocaretag.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanningBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    "Ready to Scan",
                    modifier = Modifier.padding(16.dp),
                    style = typography.title
                )

                Text(
                    "Hold your device near the NFC Tag",
                    modifier = Modifier.padding(16.dp),
                    style = typography.title
                )

                CircularProgressIndicator(
                    trackColor = colorScheme.primary
                )

                Button(onClick = onDismiss, modifier = Modifier.padding(16.dp)) {
                    Text(text = "Cancel", style = typography.bodyLarge)

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanningBottomSheetPreview() {
    ScanningBottomSheet(true, {})
}