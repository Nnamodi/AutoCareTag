package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel

data class InfoScreenState(
    val customerName: String = "",
    val customerPhoneNo: String = "",
    val vehicleModel: String = "",
    val workDone: String = "",
    val appointmentDate: String = "",
    val nextAppointmentDate: String = "",
)
