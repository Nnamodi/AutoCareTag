package dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel

data class InfoScreenState(
    val customerId: String = "",
    val customerName: String = "",
    val customerPhoneNo: String = "",
    val vehicleModel: String = "",
    var appointmentDate: String = " ",
    var nextAppointmentDate: String = "",
    val note: String = "",
)
