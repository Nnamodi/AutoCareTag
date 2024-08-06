package dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel


sealed class InfoScreenEvents {
    data class EnteredCustomerName(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerPhoneNo(val value: String):  InfoScreenEvents ()
    data class EnteredVehicleModel(val value: String):  InfoScreenEvents ()
    data class EnteredAppointmentDate(val value: String):  InfoScreenEvents ()
    data class EnteredNextAppointmentDate(val value: String):  InfoScreenEvents ()
    data class EnteredNote(val value: String):  InfoScreenEvents ()
//    data class SaveClientInfo(val id: String): InfoScreenEvents()
}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
//    data object SaveClientInfo : UiEvent()
}