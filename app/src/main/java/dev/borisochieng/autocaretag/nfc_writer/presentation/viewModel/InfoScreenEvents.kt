package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel


sealed class InfoScreenEvents {
    data class EnteredCustomerName(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerPhoneNo(val value: String):  InfoScreenEvents ()
    data class EnteredVehicleModel(val value: String):  InfoScreenEvents ()
    data class EnteredWorkDone(val value: String):  InfoScreenEvents ()

    data class EnteredAppointmentDate(val value: String):  InfoScreenEvents ()
    data class EnteredNextAppointmentDate(val value: String):  InfoScreenEvents ()
}