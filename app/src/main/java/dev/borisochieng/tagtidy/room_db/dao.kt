package dev.borisochieng.tagtidy.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClientDao {
    @Insert
    suspend fun insert(client: Client)

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

    @Query("SELECT * FROM Client")
    fun getAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM Client WHERE clientId = :clientId")
    fun getClientById(clientId: Long): LiveData<Client>
}

@Dao
interface VehicleDao {
    @Insert
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)

    @Query("SELECT * FROM Vehicle")
    fun getAllVehicles(): LiveData<List<Vehicle>>

    @Query("SELECT * FROM Vehicle WHERE vehicleId = :vehicleId")
    fun getVehicleById(vehicleId: Long): LiveData<Vehicle>
}
@Dao
interface RepairDao {
    @Insert
    suspend fun insert(repair: Repair)

    @Update
    suspend fun update(repair: Repair)

    @Delete
    suspend fun delete(repair: Repair)

    @Query("SELECT * FROM Repair")
    fun getAllRepairs(): LiveData<List<Repair>>

    @Query("SELECT * FROM Repair WHERE repairId = :repairId")
    fun getRepairById(repairId: Long): LiveData<Repair>
}
