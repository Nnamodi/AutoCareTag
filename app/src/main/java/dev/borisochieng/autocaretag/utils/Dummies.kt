package dev.borisochieng.autocaretag.utils

import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.room_db.Client as Room_dbClient

object Dummies {

	val dummyClients = listOf(
		Room_dbClient(
			name = "John Doe",
			contactInfo = "+234246496232936",
			model = "Benz E200",
			lastMaintained = "28-08-2024",
			nextAppointmentDate = "28-08-2024",
			note = ""
		),
		Room_dbClient(
			name = "John Doe",
			contactInfo = "+234246496232936",
			model = "Benz E200",
			lastMaintained = "28-08-2024",
			nextAppointmentDate = "28-08-2024",
			note = ""
		),
		Room_dbClient(
			name = "John Doe",
			contactInfo = "+234246496232936",
			model = "Benz E200",
			lastMaintained = "28-08-2024",
			nextAppointmentDate = "28-08-2024",
			note = ""
		)
	)

	val fakeClients = listOf(
		Client(
			id= "1",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "2",
			name = "Mark Joe",
			vehicleName = "Ford Ranger",
			date = "28-08-2024"
		),
		Client(
			id= "1",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "3",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)
		,
		Client(
			id= "4",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "5",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)
		,
		Client(
			id= "6",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "7",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)
		,
		Client(
			id= "8",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "9",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)
		,
		Client(
			id= "10",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "11",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)
		,
		Client(
			id= "12",
			name = "Sarah",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		),
		Client(
			id= "13",
			name = "John Doe",
			vehicleName = "Benz E200",
			date = "28-08-2024"
		)

	)

}