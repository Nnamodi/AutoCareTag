package dev.borisochieng.autocaretag.nfcwriter.domain

import dev.borisochieng.autocaretag.database.Client


fun TagInfo.toClient(): Client {
    return Client(
        clientId = customerId,
        name = customerName,
        contactInfo = customerPhoneNo,
        model = vehicleModel,
        note = workDone,
        lastMaintained = appointmentDate,
        nextAppointmentDate = nextAppointmentDate
    )
}