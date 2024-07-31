package dev.borisochieng.autocaretag.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.utils.Dummies.fakeClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClientScreenViewModel () : ViewModel(){
    private val _clientUiState = MutableStateFlow(ClientsUIState())
    val clientUiState: StateFlow<ClientsUIState> get() =  _clientUiState.asStateFlow()

    private var clientListCache = emptyList<Client>()
    init {
        getVehicleList()
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
            val filteredClients = if (keyWord.isBlank()) {
                clientListCache
            } else {
                clientListCache.filter { client ->
                    client.name.contains(keyWord, ignoreCase = true)
                }
            }

            _clientUiState.update {
                it.copy(
                    clients = filteredClients
                )
            }
        }
    }


}