package dev.borisochieng.autocaretag.nfc_reader.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.ui.commons.Avatar
import dev.borisochieng.autocaretag.ui.commons.CustomTextField
import dev.borisochieng.autocaretag.ui.commons.TopBar
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.onBackgroundVariant

@Composable
fun ClientDetailsScreen(
	uiState: ClientUiState,
	navigate: (Screens) -> Unit
) {
	val (client, vehicles) = uiState
	val note = rememberSaveable { mutableStateOf("") }

	Scaffold(
		topBar = { TopBar(stringResource(R.string.view_details), navigate) }
	) { paddingValues ->
		Column(
			Modifier
				.padding(paddingValues)
				.padding(horizontal = 16.dp)
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Avatar(
				name = client.name,
				modifier = Modifier.padding(top = 25.dp, bottom = 21.dp),
				avatarTextSize = 18.sp
			)
			DetailItem(title = stringResource(R.string.name), detail = uiState.client.name)
			DetailItem(title = stringResource(R.string.contact), detail = uiState.client.contactInfo)
			DetailItem(title = stringResource(R.string.vehicle), detail = "Mercedes")
			DetailItem(title = stringResource(R.string.car_model), detail = "2024")
			DetailItem(title = stringResource(R.string.last_issue_resolved), detail = "Suspended check")
			DetailItem(title = stringResource(R.string.last_appointment), detail = "05/15/2024")
			CustomTextField(
				header = stringResource(R.string.note),
				value = note.value,
				placeholder = stringResource(R.string.note_placeholder),
				onValueChange = { note.value = it }
			)

			Spacer(modifier = Modifier.weight(1f))

			Button(
				onClick = { navigate(Screens.ClientDetailsScreen(client.clientId.toString())) },
				modifier = Modifier
					.padding(16.dp)
					.fillMaxWidth(),
				shape = shape.button
			) {
				Text(text = stringResource(R.string.update))
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
		ClientDetailsScreen(uiState = ClientUiState()) {}
	}
}
