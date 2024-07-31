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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.ui.commons.TopBar
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import dev.borisochieng.autocaretag.ui.theme.onBackgroundVariant

@Composable
fun ClientDetailsScreen(
    uiState: ClientUiState,
    updateClientInfo: (Client) -> Unit,
    navigate: (Screens) -> Unit
) {
    val (client) = uiState
    val note = rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.background(colorScheme.background),
        topBar = { TopBar(stringResource(R.string.view_details), navigate) }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Client details",
                style = typography.title,
                color = colorScheme.primary
            )
            //client details
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Name",
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = client.name,
                style = typography.body,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Contact",
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = client.contactInfo,
                style = typography.body,
                color = Color.Gray
            )

            //vehicle details
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Vehicle Details",
                style = typography.title,
                color = colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Name/Model",
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Lamborghini Urus",
                style = typography.body,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Last Maintained",
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "28-07-2024",
                style = typography.body,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Next date of maintenance",
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "28-08-2024",
                style = typography.body,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Repair",
                style = typography.bodyLarge
            )

            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                text = "Oil change",
                style = typography.body,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))




//			DetailItem(title = stringResource(R.string.name), detail = uiState.client.name)
//			DetailItem(title = stringResource(R.string.contact), detail = uiState.client.contactInfo)
//			DetailItem(title = stringResource(R.string.vehicle), detail = "Mercedes")
//			DetailItem(title = stringResource(R.string.car_model), detail = "2024")
//			DetailItem(title = stringResource(R.string.last_issue_resolved), detail = "Suspended check")
//			DetailItem(title = stringResource(R.string.last_appointment), detail = "05/15/2024")
//			CustomTextField(
//				header = stringResource(R.string.note),
//				value = note.value,
//				placeholder = stringResource(R.string.note_placeholder),
//				onValueChange = { note.value = it }
//			)

            OutlinedButton(
                onClick = {
                    val updatedInfo = Client(
                        clientId = client.clientId,
                        name = client.name,
                        contactInfo = client.contactInfo,
                        model = client.model,
                        lastMaintained = client.lastMaintained,
                        nextAppointmentDate = client.nextAppointmentDate,
                        note = note.value.takeIf { it.isNotEmpty() } ?: client.note
                    )
                    updateClientInfo(updatedInfo)
                    navigate(Screens.ClientDetailsScreen(client.clientId.toString()))
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = shape.button,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.background
                )
            ) {
                Text(
                    text = stringResource(R.string.update),
                    style = typography.body,
                    color = colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    title: String,
    detail: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = onBackgroundVariant
            )
            Text(text = detail)
        }
        HorizontalDivider(
            Modifier.padding(top = 8.dp, bottom = 18.dp)
        )
    }
}

@Preview
@Composable
private fun ClientDetailsScreenPreview() {
    AutoCareTagTheme {
        ClientDetailsScreen(uiState = ClientUiState(), {}) {}
    }
}
