package dev.borisochieng.autocaretag.nfc_writer.domain

import dev.borisochieng.autocaretag.room_db.Client


fun TagInfo.toClient(): Client {
    return Client(
        clientId = id,
        name = customerName,
        contactInfo = customerPhoneNo,
        model = vehicleModel,
        note = workDone,
        lastMaintained = appointmentDate,
        nextAppointmentDate = nextAppointmentDate
    )
}