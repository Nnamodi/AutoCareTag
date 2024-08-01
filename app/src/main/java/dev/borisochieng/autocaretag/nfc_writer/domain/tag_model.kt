package dev.borisochieng.autocaretag.nfc_writer.domain

data class TagInfo(
    val id: Long,
    val customerName: String,
    val customerPhoneNo: String,
    val vehicleModel: String,
    val workDone: String,
    val appointmentDate: String,
    val nextAppointmentDate: String,
)
