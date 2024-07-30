package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel

import android.nfc.Tag
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.nfc_writer.data.NfcWriter
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfc_writer.domain.TagInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddInfoViewModel : ViewModel(), KoinComponent {

    private val nfcWriter: NfcWriter by inject()

    private val _customerName = mutableStateOf(
        InfoScreenState()
    )
    private val _customerPhoneNo = mutableStateOf(
        InfoScreenState()
    )
    private val _vehicleModel = mutableStateOf(
        InfoScreenState()
    )
    private val _workDone = mutableStateOf(
        InfoScreenState()
    )
    private val _appointmentDate = mutableStateOf(
        InfoScreenState()
    )
    private val _nextAppointmentDate = mutableStateOf(
        InfoScreenState()
    )
    private val nfcWriteStateFlow: MutableStateFlow<NfcWriteState<TagInfo>> = MutableStateFlow(NfcWriteState.idle())
    val nfcWriteState = nfcWriteStateFlow.asStateFlow()
    private val _buttonEnabled = mutableStateOf(false)
    val  buttonEnabled: State<Boolean> = _buttonEnabled


    val customerName: State<InfoScreenState> = _customerName
    val customerPhoneNo: State<InfoScreenState> =_customerPhoneNo
    val vehicleModel: State<InfoScreenState> = _vehicleModel
    val workDone: State<InfoScreenState> = _workDone
    val appointmentDate: State<InfoScreenState> = _appointmentDate
    val nextAppointmentDate: State<InfoScreenState> = _nextAppointmentDate


    fun onEvent(event: InfoScreenEvents) {
        when (event) {

            is InfoScreenEvents.EnteredCustomerName -> {
                _customerName.value = _customerName.value.copy(
                    customerName = event.value
                )
            }

            is InfoScreenEvents.EnteredCustomerPhoneNo -> {
                _customerPhoneNo.value = _customerPhoneNo.value.copy(
                   customerPhoneNo = event.value
                )
            }

            is InfoScreenEvents.EnteredAppointmentDate -> {
                _appointmentDate.value = _appointmentDate.value.copy(
                    appointmentDate = event.value
                )
            }
            is InfoScreenEvents.EnteredNextAppointmentDate -> {
                _nextAppointmentDate.value = _nextAppointmentDate.value.copy(
                    nextAppointmentDate = event.value
                )
            }
            is InfoScreenEvents.EnteredVehicleModel -> {
                _vehicleModel.value = _vehicleModel.value.copy(
                    vehicleModel = event.value
                )
            }
            is InfoScreenEvents.EnteredWorkDone -> {
                _workDone.value = _workDone.value.copy(
                    workDone = event.value
                )
            }
        }
    }


    fun uploadInfo(tag:Tag){
      viewModelScope.launch  {
            val tagInfo = TagInfo(
                /* Tag id should go here */
                customerName = customerName.value.customerName,
                customerPhoneNo = customerPhoneNo.value.customerPhoneNo,
                vehicleModel = vehicleModel.value.vehicleModel,
                workDone = workDone.value.workDone,
                nextAppointmentDate = nextAppointmentDate.value.nextAppointmentDate,
                appointmentDate = appointmentDate.value.appointmentDate
            )
            nfcWriteStateFlow.value = nfcWriter.writeLaundryInfoToNfcTag(tag = tag, info = tagInfo)

        }

    }
    fun writeButtonState(state:Boolean){
        nfcWriteStateFlow.value = NfcWriteState.loading()
        _buttonEnabled.value = state
    }
}