package dev.borisochieng.autocaretag.nfc_reader.ui

import dev.borisochieng.autocaretag.room_db.Client

data class ClientUiState(
	val client: Client = Client(
    name = "John Doe",
    contactInfo = "0712345678",
    model = "Toyota Corolla",
    lastMaintained = "2024-07-01",
    nextAppointmentDate = "2024-08-01",
    note = "Regular maintenance needed."
),
	val isLoading: Boolean = false
)