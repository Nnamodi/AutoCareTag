package dev.borisochieng.autocaretag.ui.manage

import androidx.compose.runtime.Immutable
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients

@Immutable
data class ClientsUIState (
    val isLoading: Boolean = false,
    val clients: List<Client> = emptyList(),
    val errorMessage: String = ""
)