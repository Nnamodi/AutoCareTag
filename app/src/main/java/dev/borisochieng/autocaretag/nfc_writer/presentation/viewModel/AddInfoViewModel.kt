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
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddInfoViewModel() : ViewModel(), KoinComponent {


    private val nfcWriter: NfcWriter by inject()
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
    private val _buttonEnabled = MutableStateFlow(false)
    val  buttonEnabled: MutableStateFlow<Boolean> = _buttonEnabled


    val customerName: State<InfoScreenState> = _customerName
    val customerPhoneNo: State<InfoScreenState> =_customerPhoneNo
    val vehicleModel: State<InfoScreenState> = _vehicleModel
    val workDone: State<InfoScreenState> = _workDone
    val appointmentDate: State<InfoScreenState> = _appointmentDate
    val nextAppointmentDate: State<InfoScreenState> = _nextAppointmentDate

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                //_playNoteState.value = true
                viewModelScope.launch {
                    Log.d("NoteId", noteId.toString())
                    voiceJournalRepository.getNote(noteId).collect { note ->
                        _noteState.value = noteState.value.copy(voiceJournal = note)
                        if (note != null) {
                            currentNoteId = note.id
                        }
                        if (note != null) {
                            _noteTitle.value = _noteTitle.value.copy(text =note.title, isHintVisible = false )
                            Log.d("NoteTitleNew", note.title)
                        }
                        if (note != null) {
                            _noteContent.value = _noteContent.value.copy(
                                text = note.content,
                                isHintVisible = false)
                        }

                        if (note != null) {
                            _created.value = _created.value.copy(
                                created = note.created,
                                isHintVisible = false
                            )
                        }

                        if (note != null) {
                            _noteColor.value = note.color
                        }
                        if (note != null) {
                            tempFileName = note.fileName
                            _noteFileName.value = _noteFileName.value.copy(
                                text = tempFileName
                            )
                        }
                        if (note != null) {
                            if (note.imageUris?.isNotEmpty()== true) {
                                tempUris = note.imageUris!!
                                _tempImageUris.value = _tempImageUris.value.copy(
                                    imageFileUris = note.imageUris
                                )
                                Log.d("Image from file", "${note.imageUris}")
                            }
                        }
                        if (note != null) {
                            if (note.tags != null) {
                                _tags.value = note.tags!!
                            }
                        }
                        if (note != null) {
                            if(note.favourite!=null) {
                                _favourite.value = _favourite.value.copy(
                                    favourite = note.favourite!!
                                )
                            }

                        }

                    }

                }
            }
        }
        Log.d("_NoteTitle",_noteTitle.value.text)
        getSelectedImageUris()

    }

    private fun areAllFieldsValid(): Boolean {
        return _customerName.value.customerName.isNotEmpty() &&
               _customerPhoneNo.value.customerPhoneNo.isNotEmpty() &&
               _vehicleModel.value.vehicleModel.isNotEmpty() &&
               _workDone.value.workDone.isNotEmpty() //&&
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
            is InfoScreenEvents.EnteredWorkDone -> {
                _workDone.value = _workDone.value.copy(
                    workDone = event.value
                )
            }
        }
        updateButtonEnabledState()
    }


    fun uploadInfo(tag: Tag,setupNfc:()->Unit){
      viewModelScope.launch  {
          setupNfc()
          val tagInfo = TagInfo(
                /* Tag id should go here */
                customerName = customerName.value.customerName,
                customerPhoneNo = customerPhoneNo.value.customerPhoneNo,
                vehicleModel = vehicleModel.value.vehicleModel,
                workDone = workDone.value.workDone,
                nextAppointmentDate = nextAppointmentDate.value.nextAppointmentDate,
                appointmentDate = appointmentDate.value.appointmentDate
            )
          nfcWriter.writeLaundryInfoToNfcTag(tag = tag, info = tagInfo).collect{
              nfcWriteStateFlow.value = it

          }

        }

    }
    fun writeButtonState(state:Boolean){
        nfcWriteStateFlow.value = NfcWriteState.loading()
        _buttonEnabled.value = state
    }
}