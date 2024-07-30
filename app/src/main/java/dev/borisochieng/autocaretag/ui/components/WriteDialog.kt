package dev.borisochieng.autocaretag.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteStatus
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel

@Composable
fun WriteDialog(
    viewModel: AddInfoViewModel,
    onCancel: () -> Unit,
    onOk: () -> Unit
) {


    // A dialog with a custom content
    Dialog(onDismissRequest = onCancel) {
        // Use a card as the content of the dialog
        Card(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 280.dp, height = 280.dp)

        ) {
            // Add your UI elements here

            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                val nfcWriteState by viewModel.nfcWriteState.collectAsState()
                when (nfcWriteState.status) {
                    NfcWriteStatus.SUCCESS -> { 
                        // Show success UI
                        Text("NFC tag writing successful!")
                    }
                    NfcWriteStatus.ERROR -> {
                        // Show error UI
                        Text("Error: ${nfcWriteState.errorMessage ?: "Unknown error"}")
                    }
                    NfcWriteStatus.LOADING -> {
                        // Show loading UI
                        CircularProgressIndicator()
                    }
                    NfcWriteStatus.IDLE -> {
                        // Show idle UI
                        Text("Idle state")
                    }
                }


            }

        }
    }
}