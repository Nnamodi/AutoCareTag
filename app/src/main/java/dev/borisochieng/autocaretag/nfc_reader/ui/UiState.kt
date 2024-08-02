package dev.borisochieng.autocaretag.nfc_reader.ui

import dev.borisochieng.autocaretag.room_db.Client

data class ClientUiState(
    val client: Client? = null,
    val clients: List<Client> = emptyList(),
)