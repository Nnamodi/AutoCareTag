package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AddInfoViewModel (
   // private val savedStateHandle: SavedStateHandle,

) : ViewModel() {

    data class LaundryInfo(
        val clothType: String,
        val customerName: String,
        val washingInstructions: String,
        val customerAddress: String,
        val amountToBePaid: String,
        val deliveryDate: String,
        val status: String
    )
    private val _clothType = mutableStateOf(
        InfoScreenState()
    )
    private val _customerName = mutableStateOf(
        InfoScreenState()
    )
    private val _customerPhoneNo = mutableStateOf(
        InfoScreenState()
    )
    private val _washingInstructions = mutableStateOf(
        InfoScreenState()
    )
    private val _customerAddress = mutableStateOf(
        InfoScreenState()
    )
    private val _amountToBePaid = mutableStateOf(
        InfoScreenState()
    )
    private val _deliveryDate = mutableStateOf(
        InfoScreenState()
    )
    private val _status = mutableStateOf(
        InfoScreenState()
    )

    val clothType: State<InfoScreenState> = _clothType
    val customerName: State<InfoScreenState> = _customerName
    val customerPhoneNo: State<InfoScreenState> =_customerPhoneNo
    val washingInstructions: State<InfoScreenState> = _washingInstructions
    val customerAddress: State<InfoScreenState> = _customerAddress
    val amountToBePaid: State<InfoScreenState> = _amountToBePaid
    val deliveryDate: State<InfoScreenState> = _deliveryDate
    val status: State<InfoScreenState> = _status


    fun onEvent(event: InfoScreenEvents) {
        when (event) {
            is InfoScreenEvents.EnteredClothType -> {
                _clothType.value = _clothType.value.copy(
                    clothType = event.value
                )
            }

            is InfoScreenEvents.EnteredCustomerName -> {
                _customerName.value = _customerName.value.copy(
                    customerName = event.value
                )
            }

            is InfoScreenEvents.EnteredCustomerPhoneNo -> {
                _customerPhoneNo.value = _customerPhoneNo.value.copy(
                   customerName  = event.value
                )
            }

            is InfoScreenEvents.EnteredWashingInstructions -> {
                _washingInstructions.value = _washingInstructions.value.copy(washingInstructions = event.value)
            }

            is InfoScreenEvents.EnteredCustomerAddress -> {
                _customerAddress.value = _customerAddress.value.copy(
                    customerAddress = event.value
                )
            }

            is InfoScreenEvents.EnteredAmountToBePaid -> {
                _amountToBePaid.value = _amountToBePaid.value.copy(
                    amountToBePaid = event.value
                )
            }

            is InfoScreenEvents.EnteredDeliveryDate -> {
                _deliveryDate.value = _deliveryDate.value.copy(
                    deliveryDate = event.value
                )
            }

            is InfoScreenEvents.EnteredStatus -> {
                _status.value = _status.value.copy(
                    status = event.value
                )
            }
        }
    }

}