package dev.borisochieng.autocaretag.room_db.repository

import dev.borisochieng.autocaretag.room_db.Client
import dev.borisochieng.autocaretag.room_db.ClientDao
import dev.borisochieng.autocaretag.room_db.Repair
import dev.borisochieng.autocaretag.room_db.RepairDao
import dev.borisochieng.autocaretag.room_db.Vehicle
import dev.borisochieng.autocaretag.room_db.VehicleDao
import kotlinx.coroutines.flow.Flow

class ClientRepositoryImpl(private val clientDao: ClientDao) : ClientRepository {
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

    override suspend fun getClientById(clientId: Long): Flow<Client?> {
        return clientDao.getClientById(clientId) // Handle null case appropriately
    }
}

class VehicleRepositoryImpl(private val vehicleDao: VehicleDao) : VehicleRepository {
    override suspend fun insert(vehicle: Vehicle){
        return vehicleDao.insert(vehicle)
    }

    override suspend fun update(vehicle: Vehicle) {
        return vehicleDao.update(vehicle)
    }

    override suspend fun delete(vehicle: Vehicle) {
        return vehicleDao.delete(vehicle)
    }

    override suspend fun getAllVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAllVehicles()
    }

    override suspend fun getVehiclesById(clientId: Long): Flow<List<Vehicle>> {
        return vehicleDao.getVehiclesById(clientId)
    }
}

class RepairRepositoryImpl(private val repairDao: RepairDao) : RepairRepository {
    override suspend fun insert(repair: Repair){
        return repairDao.insert(repair)
    }

    override suspend fun update(repair: Repair) {
        return repairDao.update(repair)
    }

    override suspend fun delete(repair: Repair) {
        return repairDao.delete(repair)
    }

    override suspend fun getAllRepairs(): Flow<List<Repair>> {
        return repairDao.getAllRepairs()
    }

    override suspend fun getRepairById(repairId: Long): Flow<Repair?> {
        return repairDao.getRepairById(repairId)// Handle null case appropriately
    }

    override suspend fun getRepairByVehicleId(vehicleId: Long): Flow<List<Repair>> {
        return repairDao.getRepairByVehicleId(vehicleId)
    }
}

