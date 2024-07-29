package dev.borisochieng.autocaretag.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: Client)

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

    @Query("SELECT * FROM Client")
    fun getAllClients(): Flow<List<Client>>

    @Query("SELECT * FROM Client WHERE clientId = :clientId")
    fun getClientById(clientId: Long): Flow<Client>
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
    fun getAllVehicles(): Flow<List<Vehicle>>

    @Query("SELECT * FROM Vehicle WHERE clientId = :clientId")
    fun getVehiclesById(clientId: Long): Flow<List<Vehicle>>
}
@Dao
interface RepairDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repair: Repair)

    @Update
    suspend fun update(repair: Repair)

    @Delete
    suspend fun delete(repair: Repair)

    @Query("SELECT * FROM Repair")
    fun getAllRepairs(): Flow<List<Repair>>

    @Query("SELECT * FROM Repair WHERE repairId = :repairId")
    fun getRepairById(repairId: Long): Flow<Repair?>

    @Query("SELECT * FROM Repair WHERE vehicleId = :vehicleId")
    fun getRepairByVehicleId(vehicleId: Long): Flow<List<Repair>>
}
