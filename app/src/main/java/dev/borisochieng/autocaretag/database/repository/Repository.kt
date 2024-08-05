package dev.borisochieng.autocaretag.database.repository

import dev.borisochieng.autocaretag.database.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun insert(client: Client)
    suspend fun update(client: Client)
    suspend fun delete(client: Client)
    suspend fun getAllClients(): Flow<List<Client>>
    suspend fun getClientById(clientId: String): Flow<Client?>
}


