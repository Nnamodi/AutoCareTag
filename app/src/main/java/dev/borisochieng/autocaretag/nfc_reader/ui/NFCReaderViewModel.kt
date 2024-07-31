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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NFCReaderViewModel : ViewModel(), KoinComponent {

	private val nfcReaderRepository: NFCReaderRepository by inject()
	private val clientRepository: ClientRepository by inject()

	private val _tagInfo = MutableStateFlow<State<TagInfo>>(State.Loading)
	private val _clientUiState = MutableStateFlow(ClientUiState())
	var clientUiState by mutableStateOf(_clientUiState.value); private set
	var nfcIsEnabled by mutableStateOf(false); private set
	var tagIsEmpty by mutableStateOf(true); private set

	val nfcReadState = _tagInfo.asStateFlow()

	init {
		viewModelScope.launch {
			_tagInfo.collect {
				if (it !is State.Success) return@collect
				tagIsEmpty = it.data.vehicleModel.isEmpty()
				fetchClientDetails(it.data.id)
			}
		}
		viewModelScope.launch {
			_clientUiState.collect {
				clientUiState = it
			}
		}
	}

	fun readNFCTag(intent: Intent) {
		tagIsEmpty = true
		_tagInfo.value = nfcReaderRepository.readNFCTag(intent)
	}

	private fun fetchClientDetails(clientId: Long) {
		viewModelScope.launch {
			clientRepository.getClientById(clientId).collect { client ->
				if (client == null) return@collect
				_clientUiState.update { it.copy(client = client) }
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

	fun getClients(clientId: Long) =
		viewModelScope.launch {
			clientRepository.getClientById(clientId).collect{ clientFromDB ->
				_clientUiState.update {
					it.copy(
						client = clientFromDB!!
					)
				}

			}


		}

}