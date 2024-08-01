package dev.borisochieng.autocaretag.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClientScreenViewModel() : ViewModel(), KoinComponent {
    private val clientRepository:ClientRepository by inject()
    private val _clientUiState = MutableStateFlow(ClientsUIState())
    val clientUiState: StateFlow<ClientsUIState> get() = _clientUiState.asStateFlow()

    private var clientListCache = emptyList<Client>()
    private var currentSearchKeyword = ""

    init {
        getClients()
    }

    private fun getVehicleList() =

        //TODO(Toby read clients from DB)
        viewModelScope.launch {
            _clientUiState.update {
                it.copy(
                    clients = fakeClients, // list from database
                    errorMessage = ""
                )
            }
            clientListCache = fakeClients
        }

    fun searchClient(keyWord: String) {
        viewModelScope.launch {
           currentSearchKeyword = keyWord
            applyFilter()
        }
    }

   private fun getClients() =
        viewModelScope.launch {
            clientRepository.getAllClients().collect{ clients ->
                clientListCache = clients
                applyFilter()

            }
        }

    private fun applyFilter() {
        val filteredClients = if(currentSearchKeyword.isBlank()){
            clientListCache
        }else {
            clientListCache.filter { client ->
                client.name.contains(currentSearchKeyword, ignoreCase = true) ||
                        client.model.contains(currentSearchKeyword, ignoreCase = true)
            }
        }
        _clientUiState.update {
            it.copy(clients = filteredClients)
        }
    }


}