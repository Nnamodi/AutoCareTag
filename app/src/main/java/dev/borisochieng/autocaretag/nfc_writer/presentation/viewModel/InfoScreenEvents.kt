package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel

sealed class InfoScreenEvents {
    data class EnteredClothType(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerName(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerPhoneNo(val value: String):  InfoScreenEvents ()
    data class EnteredWashingInstructions(val value: String):  InfoScreenEvents ()
    data class EnteredCustomerAddress(val value: String):  InfoScreenEvents ()
    data class EnteredAmountToBePaid(val value: String):  InfoScreenEvents ()
    data class EnteredDeliveryDate(val value: String):  InfoScreenEvents ()
    data class EnteredStatus(val value: String):  InfoScreenEvents ()
}