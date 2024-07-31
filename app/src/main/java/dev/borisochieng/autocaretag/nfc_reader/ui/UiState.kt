package dev.borisochieng.autocaretag.nfc_reader.ui

import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.utils.Dummies.dummyClients

data class ClientUiState(
	val client: Client = dummyClients[0],
	val isLoading: Boolean = false
)