package dev.borisochieng.autocaretag.nfc_reader.ui

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.nfc_reader.repository.NFCReaderRepository
import dev.borisochieng.autocaretag.nfc_reader.data.State
import dev.borisochieng.autocaretag.room_db.Vehicle
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NFCReaderViewModel : ViewModel(), KoinComponent {

	private val nfcReaderRepository: NFCReaderRepository by inject()
	private val clientRepository: ClientRepository by inject()

	private val _vehicleInfo = MutableStateFlow<State<Vehicle>>(State.Loading)
	private val _clientUiState = MutableStateFlow(ClientUiState())
	var clientUiState by mutableStateOf(_clientUiState.value); private set
	var tagIsEmpty by mutableStateOf(false); private set

	init {
		viewModelScope.launch {
			_vehicleInfo.collect {
				if (it !is State.Success) return@collect
				tagIsEmpty = it.data.clientId.toString().isEmpty()
				fetchClientDetails(it.data.clientId)
			}
		}
		viewModelScope.launch {
			_clientUiState.collect {
				clientUiState = it
			}
		}
	}

	fun readNFCTag(intent: Intent) {
		_vehicleInfo.value = nfcReaderRepository.readNFCTag(intent)
	}

	private fun fetchClientDetails(clientId: Long) {
		viewModelScope.launch {
			clientRepository.getClientById(clientId).collect { client ->
				if (client == null) return@collect
				_clientUiState.update { it.copy(client = client) }
			}
		}
	}

}