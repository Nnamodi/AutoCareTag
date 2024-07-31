package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel


sealed class InfoScreenEvents {
    data class EnteredCustomerName(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerPhoneNo(val value: String):  InfoScreenEvents ()
    data class EnteredVehicleModel(val value: String):  InfoScreenEvents ()
    data class EnteredAppointmentDate(val value: String):  InfoScreenEvents ()
    data class EnteredNextAppointmentDate(val value: String):  InfoScreenEvents ()
    data class EnteredNote(val value: String):  InfoScreenEvents ()
    data object SaveClientInfo: InfoScreenEvents()

}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object SaveClientInfo : UiEvent()


}