package dev.borisochieng.autocaretag.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteStatus
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WriteDialog(
    viewModel: AddInfoViewModel,
    onCancel: () -> Unit,
    onOk: () -> Unit,
    navigateToClientDetails: () -> Unit
) {
    var readyToScan by remember { mutableStateOf("") }
    var supportingText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // A dialog with a custom content
    Dialog(onDismissRequest = onCancel) {
        // Use a card as the content of the dialog
        Card(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 280.dp, height = 280.dp)

        ) {
            // Add your UI elements here
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val nfcWriteState by viewModel.nfcWriteState.collectAsState()
                when (nfcWriteState.status) {
                    NfcWriteStatus.SUCCESS -> {
                        // Show success UI
                        readyToScan = "Successful"
                        supportingText = "Details imported successfully"
                        Text(
                            text = readyToScan,
                            style = typography.title,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = supportingText,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Green, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(100.dp),
                                imageVector = Icons.Rounded.Check,
                                tint = Color.White,
                                contentDescription = "Successful"
                            )
                        }

                        LaunchedEffect(Unit) {
                            scope.launch {
                                delay(2000L)
                                navigateToClientDetails()
                            }


                        }
                    }

                    NfcWriteStatus.ERROR -> {
                        // Show error UI
                        supportingText =
                            nfcWriteState.errorMessage ?: "Something went wrong, please try again"

                        Text(
                            text = supportingText,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(colorScheme.error, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(100.dp),
                                imageVector = Icons.Rounded.Close,
                                tint = Color.White,
                                contentDescription = "Error"
                            )
                        }
                    }

                    NfcWriteStatus.LOADING -> {
                        // Show loading UI
                        readyToScan = "Ready to Scan"
                        supportingText = "Hold your device near the NFC Tag"
                        Text(
                            text = readyToScan,
                            style = typography.title,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = supportingText,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(shape = CircleShape, color = Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.scanning),
                                contentDescription = "Scanning",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    NfcWriteStatus.IDLE -> {
                        // Show idle UI
                        readyToScan = "Ready to Scan"
                        supportingText = "Hold your device near the NFC Tag"
                        Text(
                            text = readyToScan,
                            style = typography.title,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = supportingText,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(shape = CircleShape, color = Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.scanning),
                                contentDescription = "Scanning",
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun WriteDialogPreview() {
    WriteDialog(viewModel = viewModel(), onCancel = {}, onOk = {}, {})


}