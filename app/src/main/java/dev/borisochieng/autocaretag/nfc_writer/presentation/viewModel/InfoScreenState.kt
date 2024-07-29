package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel



data class InfoScreenState(
    val clothType: String = "",
    val customerName: String = "",
    val customerPhoneNo: String = "",
    val washingInstructions: String= "",
    val customerAddress: String = "",
    val amountToBePaid: String = "",
    val deliveryDate: String = "",
    val status: String= "",
)