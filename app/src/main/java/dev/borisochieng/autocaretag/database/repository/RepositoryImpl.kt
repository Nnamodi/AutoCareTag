package dev.borisochieng.autocaretag.database.repository

import dev.borisochieng.autocaretag.database.Client
import dev.borisochieng.autocaretag.database.ClientDao
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClientRepositoryImpl : ClientRepository, KoinComponent {
    private val clientDao: ClientDao by inject()

    override suspend fun insert(client: Client) {
        return clientDao.insert(client)
    }

    override suspend fun update(client: Client) {
        return clientDao.update(client)
    }

    override suspend fun delete(client: Client) {
        return clientDao.delete(client)
    }

    override suspend fun getAllClients(): Flow<List<Client>> {
        return clientDao.getAllClients()
    }

    override suspend fun getClientById(clientId: String): Flow<Client?> {
        return clientDao.getClientById(clientId) // Handle null case appropriately
    }
}

