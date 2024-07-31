package dev.borisochieng.autocaretag.nfc_reader.ui

import dev.borisochieng.autocaretag.room_db.Client


data class ClientUiState(
	val client: Client = Client(name = "Jay Abbah",
		contactInfo = "+23472537353",
		note = "test note",
        lastMaintained = " 28-07-2024",
        nextAppointmentDate = "28-08-2024",
        model = "Lamborghini Urus"
		),
	val isLoading: Boolean = false,
)
