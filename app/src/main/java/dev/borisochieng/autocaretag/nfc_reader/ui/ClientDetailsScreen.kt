package dev.borisochieng.autocaretag.nfc_reader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.ui.commons.TopBar
import dev.borisochieng.autocaretag.ui.components.PrimaryButton
import dev.borisochieng.autocaretag.ui.components.ScreenTitle
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import dev.borisochieng.autocaretag.ui.theme.onBackgroundVariant
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailsScreen(
    viewModel: NFCReaderViewModel,
    updateClientInfo: (Client) -> Unit,
    navigate: (Screens) -> Unit
) {
    // Collect the latest state from the ViewModel
    val uiState = viewModel.clientUiState.collectAsState()

    val client by rememberSaveable {
        mutableStateOf(uiState.value.client)

    }

    // Scaffold layout for the screen
    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    ScreenTitle()
                }
            )
        }
    ) { paddingValues ->
        // Column layout for the content
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header text for client details
            Text(
                text = "Client details",
                style = typography.title,
                color = colorScheme.primary
            )

            // Display client details if available, otherwise show a loading indicator
            client.let { client ->
                client?.name?.let { ClientDetailSection("Name", it) }
                if (client != null) {
                    ClientDetailSection("Contact", client.contactInfo)
                }
                Text(
                    text = "Vehicle Details",
                    style = typography.title,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (client != null) {
                    ClientDetailSection("Name/Model", client.model)
                }
                if (client != null) {
                    ClientDetailSection("Last Maintained", client.lastMaintained)
                }
                if (client != null) {
                    ClientDetailSection("Next date of maintenance", client.nextAppointmentDate)
                }
                if (client != null) {
                    ClientDetailSection("Repair", client.note)
                }
            }

            // Spacer to push the button to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // OutlinedButton to update client info
            OutlinedButton(
                onClick = {
                    client.let { client: Client? ->
                        val updatedInfo = client
                        if (updatedInfo != null) {
                            updateClientInfo(updatedInfo)
                        }
                        navigate(Screens.AddScreen)
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = shape.button,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorScheme.background,
                )
            ) {
                Text(
                    text = "Back to home",
                    style = typography.bodyLarge,
                    color = colorScheme.primary
                )
            }
        }
    }
}

// Helper composable to display client details
@Composable
fun ClientDetailSection(label: String, detail: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = label,
        style = typography.bodyLarge
    )
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = detail,
        style = typography.body,
        color = Color.Gray
    )
}

// Preview function for ClientDetailsScreen
@Preview
@Composable
private fun ClientDetailsScreenPreview() {
    AutoCareTagTheme {
        ClientDetailsScreen(viewModel = viewModel(), {}) {}
    }
}
