package dev.borisochieng.autocaretag.nfc_reader.ui

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.nfc_reader.data.State
import dev.borisochieng.autocaretag.nfc_reader.repository.NFCReaderRepository
import dev.borisochieng.autocaretag.nfc_writer.domain.TagInfo
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NFCReaderViewModel : ViewModel(), KoinComponent {

    private val nfcReaderRepository: NFCReaderRepository by inject()
    private val clientRepository: ClientRepository by inject()

    private val _tagInfo = MutableStateFlow<State<TagInfo>>(State.Loading)
    private val _clientUiState = MutableStateFlow(ClientUiState())
    val clientUiState: StateFlow<ClientUiState> get() = _clientUiState

    private var nfcIsEnabled by mutableStateOf(false);
    var tagIsEmpty by mutableStateOf(true); private set

    val nfcReadState = _tagInfo.asStateFlow()

    init {
        viewModelScope.launch {
            _tagInfo.collect { state ->
                if (state !is State.Success) return@collect
                tagIsEmpty = state.data.vehicleModel.isEmpty()
                fetchClientDetails(state.data.id)
            }
        }
        viewModelScope.launch {
            _clientUiState.collect { state ->
                // Update the clientUiState value
            }
        }
        getClients()
    }

    fun readNFCTag(intent: Intent) {
        tagIsEmpty = true
        _tagInfo.value = nfcReaderRepository.readNFCTag(intent)
    }

    private fun fetchClientDetails(clientId: Long) {
        viewModelScope.launch {
            clientRepository.getClientById(clientId).collect { client ->
                if (client != null) {
                    _clientUiState.update { it.copy(client = client) }
                }
            }
        }
    }

    fun updateClientDetails(client: Client) {
        viewModelScope.launch {
            clientRepository.update(client)
        }
    }

    fun toggleNfcEnabledStatus(enabled: Boolean) {
        nfcIsEnabled = enabled
    }

    private fun getClients() = viewModelScope.launch {
        clientRepository.getAllClients().collect { clientsFromDB ->
            _clientUiState.update { it.copy(clients = clientsFromDB) }
        }
    }
}
