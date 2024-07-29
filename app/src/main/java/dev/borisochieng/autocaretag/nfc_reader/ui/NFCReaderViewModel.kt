package dev.borisochieng.autocaretag.nfc_reader.ui

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.nfc_reader.repository.NFCReaderRepository
import dev.borisochieng.autocaretag.nfc_reader.data.LaundryInfo
import dev.borisochieng.autocaretag.nfc_reader.data.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NFCReaderViewModel : ViewModel(), KoinComponent {

	private val nfcReaderRepository: NFCReaderRepository by inject()

	private val _laundryInfo = MutableStateFlow<State<LaundryInfo>>(State.Loading)
	var laundryInfo by mutableStateOf(_laundryInfo.value); private set

	init {
		viewModelScope.launch {
			_laundryInfo.collect {
				laundryInfo = it
			}
		}
	}

	fun readNFCTag(intent: Intent) {
		_laundryInfo.value = nfcReaderRepository.readNFCTag(intent)
	}

}