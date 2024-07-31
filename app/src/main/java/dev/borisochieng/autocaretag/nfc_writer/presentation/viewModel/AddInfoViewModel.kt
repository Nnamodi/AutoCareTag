package dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel

import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.nfc_writer.data.NfcWriter
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfc_writer.domain.TagInfo
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.room_db.InvalidClientException
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddInfoViewModel() : ViewModel(), KoinComponent {


    val nfcWriter: NfcWriter by inject()
    private val savedStateHandle: SavedStateHandle by inject()
    private val clientRepository: ClientRepository by inject()

    private val _customerName = mutableStateOf(
        InfoScreenState()
    )
    private val _customerPhoneNo = mutableStateOf(
        InfoScreenState()
    )
    private val _vehicleModel = mutableStateOf(
        InfoScreenState()
    )
    private val _appointmentDate = mutableStateOf(
        InfoScreenState()
    )
    private val _nextAppointmentDate = mutableStateOf(
        InfoScreenState()
    )
    private val _note = mutableStateOf( InfoScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val nfcWriteStateFlow: MutableStateFlow<NfcWriteState<TagInfo>> = MutableStateFlow(NfcWriteState.idle())
    val nfcWriteState = nfcWriteStateFlow
    private val _buttonEnabled = MutableStateFlow(false)
    val  buttonEnabled: MutableStateFlow<Boolean> = _buttonEnabled


    val customerName: State<InfoScreenState> = _customerName
    val customerPhoneNo: State<InfoScreenState> =_customerPhoneNo
    val vehicleModel: State<InfoScreenState> = _vehicleModel
    val appointmentDate: State<InfoScreenState> = _appointmentDate
    val nextAppointmentDate: State<InfoScreenState> = _nextAppointmentDate
    val note: State<InfoScreenState> = _note

    init {
        savedStateHandle.get<Long>("clientId")?.let { clientId ->
            if (clientId != 0L) {
                //_playNoteState.value = true
                viewModelScope.launch {
                    Log.d("NoteId", clientId.toString())
                    clientRepository.getClientById(clientId).collect { client ->

                        if (client != null) {
                            _customerName.value = _customerName.value.copy(
                                customerName = client.name,
                            )
                        }
                        if (client != null) {
                            _customerPhoneNo.value = _customerPhoneNo.value.copy(
                                customerPhoneNo = client.contactInfo
                            )
                        }

                        if (client != null) {
                            _vehicleModel.value = _vehicleModel.value.copy(
                                vehicleModel = client.model
                            )

                        }
                        if (client != null) {
                            _appointmentDate.value = _appointmentDate.value.copy(
                                appointmentDate = client.lastMaintained
                            )
                        }
                        if (client != null) {
                            _nextAppointmentDate.value = _nextAppointmentDate.value.copy(
                                nextAppointmentDate = client.nextAppointmentDate
                            )
                        }
                        if (client != null) {
                            _note.value = _note.value.copy(
                                note = client.note
                            )
                        }

                    }

                }
            }
        }

    }

    private fun areAllFieldsValid(): Boolean {
        return _customerName.value.customerName.isNotEmpty() &&
               _customerPhoneNo.value.customerPhoneNo.isNotEmpty() &&
               _vehicleModel.value.vehicleModel.isNotEmpty() &&
               _note.value.note.isNotEmpty() //&&
//               _appointmentDate.value.appointmentDate != null &&
//               _nextAppointmentDate.value.nextAppointmentDate != null
    }

    private fun updateButtonEnabledState() {
        _buttonEnabled.value = areAllFieldsValid()
    }


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
            is InfoScreenEvents.EnteredNote -> {
                _note.value = _note.value.copy(
                    note = event.value
                )
            }

            InfoScreenEvents.SaveClientInfo -> {
                viewModelScope.launch {
                    try {

                        val client = Client(
                            name = customerName.value.customerName,
                            contactInfo = customerPhoneNo.value.customerPhoneNo,
                            model = vehicleModel.value.vehicleModel,
                            lastMaintained = appointmentDate.value.appointmentDate,
                            nextAppointmentDate = nextAppointmentDate.value.nextAppointmentDate,
                            note = note.value.note
                        )
                        clientRepository.insert(client)
                        _eventFlow.emit(UiEvent.SaveClientInfo)
                        _eventFlow.emit(UiEvent.ShowSnackbar("Client saved!"))
                    } catch (e: InvalidClientException) {

                        Log.d("Client", "could not save")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save client"
                            )
                        )

                    }
                }
            }
        }
    }


    fun uploadInfo(tag: Tag,setupNfc:()->Unit){
      viewModelScope.launch  {
          setupNfc()
          val tagInfo = TagInfo(
                /* Tag id should go here */
                customerName = customerName.value.customerName,
                customerPhoneNo = customerPhoneNo.value.customerPhoneNo,
                vehicleModel = vehicleModel.value.vehicleModel,
                workDone = note.value.note,
                nextAppointmentDate = nextAppointmentDate.value.nextAppointmentDate,
                appointmentDate = appointmentDate.value.appointmentDate
            )
          nfcWriter.writeLaundryInfoToNfcTag(tag = tag, info = tagInfo)




        }

    }

    fun writeButtonState(state:Boolean){
        nfcWriteStateFlow.value = NfcWriteState.loading()
        _buttonEnabled.value = state
    }
}