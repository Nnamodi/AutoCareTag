package dev.borisochieng.autocaretag.utils


import dev.borisochieng.autocaretag.database.Client
import dev.borisochieng.autocaretag.database.Client as Room_dbClient

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
        clientId = "1",
        name = "Alice Johnson",
        contactInfo = "alice.johnson@example.com",
        note = "Regular customer.",
        model = "Toyota Camry",
        lastMaintained = "2024-06-15",
        nextAppointmentDate = "2024-08-01"
    ),
    Client(
        clientId = "2",
        name = "Bob Smith",
        contactInfo = "bob.smith@example.com",
        note = "Needs urgent repair.",
        model = "Honda Accord",
        lastMaintained = "2024-07-10",
        nextAppointmentDate = "2024-09-05"
    ),
    Client(
        clientId = "3",
        name = "Charlie Brown",
        contactInfo = "charlie.brown@example.com",
        note = "New customer.",
        model = "Ford Focus",
        lastMaintained = "2024-07-20",
        nextAppointmentDate = "2024-08-15"
    ),
    Client(
        clientId = "4",
        name = "Daisy Miller",
        contactInfo = "daisy.miller@example.com",
        note = "Regular oil change.",
        model = "Chevrolet Malibu",
        lastMaintained = "2024-06-30",
        nextAppointmentDate = "2024-08-20"
    ),
    Client(
        clientId = "5",
        name = "Ethan Hunt",
        contactInfo = "ethan.hunt@example.com",
        note = "Check engine light issue.",
        model = "Nissan Altima",
        lastMaintained = "2024-07-05",
        nextAppointmentDate = "2024-08-10"
    ),
    Client(
        clientId = "6",
        name = "Fiona Green",
        contactInfo = "fiona.green@example.com",
        note = "Tire rotation needed.",
        model = "Volkswagen Jetta",
        lastMaintained = "2024-06-25",
        nextAppointmentDate = "2024-09-01"
    ),
    Client(
        clientId = "7",
        name = "George Black",
        contactInfo = "george.black@example.com",
        note = "Battery replacement required.",
        model = "BMW 3 Series",
        lastMaintained = "2024-07-01",
        nextAppointmentDate = "2024-08-05"
    ),
    Client(
        clientId = "8",
        name = "Hannah White",
        contactInfo = "hannah.white@example.com",
        note = "Routine maintenance.",
        model = "Mercedes-Benz C-Class",
        lastMaintained = "2024-06-18",
        nextAppointmentDate = "2024-08-25"
    ),
    Client(
        clientId = "9",
        name = "Ian Gray",
        contactInfo = "ian.gray@example.com",
        note = "Needs brake inspection.",
        model = "Subaru Impreza",
        lastMaintained = "2024-07-15",
        nextAppointmentDate = "2024-09-10"
    ),
    Client(
        clientId = "10",
        name = "Julia Roberts",
        contactInfo = "julia.roberts@example.com",
        note = "Oil leak detected.",
        model = "Kia Optima",
        lastMaintained = "2024-07-25",
        nextAppointmentDate = "2024-08-30"
    )
)




}