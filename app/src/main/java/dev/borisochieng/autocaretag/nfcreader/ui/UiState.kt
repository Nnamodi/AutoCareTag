package dev.borisochieng.autocaretag.nfcreader.ui

import dev.borisochieng.autocaretag.database.Client

data class ClientUiState(
    val client: Client? = null,
    val clients: List<Client> = emptyList(),
)