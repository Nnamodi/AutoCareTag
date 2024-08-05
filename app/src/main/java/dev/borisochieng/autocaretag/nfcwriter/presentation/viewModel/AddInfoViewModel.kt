package dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel

import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.database.Client
import dev.borisochieng.autocaretag.database.repository.ClientRepository
import dev.borisochieng.autocaretag.nfcreader.repository.ClientId
import dev.borisochieng.autocaretag.nfcwriter.data.NfcWriter
import dev.borisochieng.autocaretag.nfcwriter.domain.NfcWriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID.randomUUID

class AddInfoViewModel : ViewModel(), KoinComponent {


    private val nfcWriter: NfcWriter by inject()
    private val savedStateHandle: SavedStateHandle by inject()
    private val clientRepository: ClientRepository by inject()

    private val _customerId = mutableStateOf(InfoScreenState())
    private val _customerName = mutableStateOf(InfoScreenState())
    private val _customerPhoneNo = mutableStateOf(InfoScreenState())
    private val _vehicleModel = mutableStateOf(InfoScreenState())
    private val _appointmentDate = mutableStateOf(InfoScreenState())
    private val _nextAppointmentDate = mutableStateOf(InfoScreenState())
    private val _note = mutableStateOf(InfoScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _nfcWriteState: MutableStateFlow<NfcWriteState<ClientId>> =
        MutableStateFlow(NfcWriteState.idle())
    val nfcWriteState: StateFlow<NfcWriteState<ClientId>> get() = _nfcWriteState

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: MutableStateFlow<Boolean> = _buttonEnabled


    private val customerId: State<InfoScreenState> = _customerId
    val customerName: State<InfoScreenState> = _customerName
    val customerPhoneNo: State<InfoScreenState> = _customerPhoneNo
    val vehicleModel: State<InfoScreenState> = _vehicleModel
    val appointmentDate: State<InfoScreenState> = _appointmentDate
    val nextAppointmentDate: State<InfoScreenState> = _nextAppointmentDate
    val note: State<InfoScreenState> = _note

    init {
        savedStateHandle.get<String>("clientId")?.let { clientId ->
            if (clientId.isNotEmpty()) {
                //_playNoteState.value = true
                viewModelScope.launch {
                    Log.d("NoteId", clientId)
                    clientRepository.getClientById(clientId).collect { client ->

                        if (client != null) {
                            _customerId.value = _customerId.value.copy(
                                customerId = client.clientId,
                            )
                        }
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

                        updateButtonEnabledState()

                    }

                }
            }
        }

    }

    private fun saveClientToDB(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            clientRepository.insert(client)
        }
    }

    private fun areAllFieldsValid(): Boolean {
        return _customerName.value.customerName.isNotEmpty() &&
                _customerPhoneNo.value.customerPhoneNo.isNotEmpty() &&
                _vehicleModel.value.vehicleModel.isNotEmpty() &&
                _note.value.note.isNotEmpty() &&
                _appointmentDate.value.appointmentDate.isNotEmpty() &&
                _nextAppointmentDate.value.nextAppointmentDate.isNotEmpty()
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
                updateButtonEnabledState()
            }

            is InfoScreenEvents.EnteredCustomerPhoneNo -> {
                _customerPhoneNo.value = _customerPhoneNo.value.copy(
                    customerPhoneNo = event.value
                )
                updateButtonEnabledState()
            }

            is InfoScreenEvents.EnteredAppointmentDate -> {
                _appointmentDate.value = _appointmentDate.value.copy(
                    appointmentDate = event.value
                )
                updateButtonEnabledState()
            }

            is InfoScreenEvents.EnteredNextAppointmentDate -> {
                _nextAppointmentDate.value = _nextAppointmentDate.value.copy(
                    nextAppointmentDate = event.value
                )
                updateButtonEnabledState()
            }

            is InfoScreenEvents.EnteredVehicleModel -> {
                _vehicleModel.value = _vehicleModel.value.copy(
                    vehicleModel = event.value
                )
                updateButtonEnabledState()
            }

            is InfoScreenEvents.EnteredNote -> {
                _note.value = _note.value.copy(
                    note = event.value
                )
                updateButtonEnabledState()
            }

//            is InfoScreenEvents.SaveClientInfo -> {
//                viewModelScope.launch {
//                    try {
////                        _eventFlow.emit(UiEvent.SaveClientInfo)
//                        _eventFlow.emit(UiEvent.ShowSnackbar("Client saved!"))
//                    } catch (e: InvalidClientException) {
//                        Log.d("Client", "could not save")
//                        _eventFlow.emit(
//                            UiEvent.ShowSnackbar(
//                                message = e.message ?: "Couldn't save client"
//                            )
//                        )
//                    }
//                }
//            }
        }
    }

    fun uploadIdToTag(tag: Tag) {
        _nfcWriteState.value = NfcWriteState.loading()
        val clientId = randomUUID().toString()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                nfcWriter.writeLaundryInfoToNfcTag(
                    tag = tag,
                    clientId = clientId,
                    onSuccess = {
                        viewModelScope.launch {
                            val client = Client(
                                clientId = clientId,
                                name = customerName.value.customerName,
                                contactInfo = customerPhoneNo.value.customerPhoneNo,
                                model = vehicleModel.value.vehicleModel,
                                note = note.value.note,
                                lastMaintained = appointmentDate.value.appointmentDate,
                                nextAppointmentDate = nextAppointmentDate.value.nextAppointmentDate
                            )
                            saveClientToDB(client)
                            _nfcWriteState.value = NfcWriteState.success(client.clientId)
                            Log.d("TagInfo", client.clientId)
                            _eventFlow.emit(UiEvent.ShowSnackbar("NFC Tag written successfully!"))
                            clearAllFields()
                        }
                    },
                    onError = { error ->
                        viewModelScope.launch {
                            _nfcWriteState.value = NfcWriteState.error(error)
                            _eventFlow.emit(UiEvent.ShowSnackbar(error))
                        }
                    }
                )
            }
        }
    }

    private fun clearAllFields() {
        _customerId.value = _customerId.value.copy(customerId = "")
        _customerName.value = _customerName.value.copy(customerName = "")
        _customerPhoneNo.value = _customerPhoneNo.value.copy(customerPhoneNo = "")
        _vehicleModel.value = _vehicleModel.value.copy(vehicleModel = "")
        _appointmentDate.value = _appointmentDate.value.copy(appointmentDate = "")
        _nextAppointmentDate.value = _nextAppointmentDate.value.copy(nextAppointmentDate = "")
        _note.value = _note.value.copy(note = "")

        // Update button state after clearing fields
        updateButtonEnabledState()
    }

    fun writeButtonState(state: Boolean) {
        _nfcWriteState.value = NfcWriteState.loading()
        _buttonEnabled.value = state
    }
}