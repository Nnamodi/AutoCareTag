package dev.borisochieng.autocaretag.room_db.repository

import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.room_db.Repair
import dev.borisochieng.autocaretag.room_db.Vehicle
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun insert(client: Client)
    suspend fun update(client: Client)
    suspend fun delete(client: Client)
    suspend fun getAllClients(): Flow<List<Client>>
    suspend fun getClientById(clientId: Long): Flow<Client?>
}

interface VehicleRepository {
    suspend fun insert(vehicle: Vehicle)
    suspend fun update(vehicle: Vehicle)
    suspend fun delete(vehicle: Vehicle)
    suspend fun getAllVehicles(): Flow<List<Vehicle>>
    suspend fun getVehiclesById(clientId: Long): Flow<List<Vehicle>>
}

interface RepairRepository {
    suspend fun insert(repair: Repair)
    suspend fun update(repair: Repair)
    suspend fun delete(repair: Repair)
    suspend fun getAllRepairs(): Flow<List<Repair>>
    suspend fun getRepairById(repairId: Long): Flow<Repair?>
    suspend fun getRepairByVehicleId(vehicleId: Long): Flow<List<Repair>>
}
