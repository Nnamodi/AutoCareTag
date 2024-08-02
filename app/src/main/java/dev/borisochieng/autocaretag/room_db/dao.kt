package dev.borisochieng.autocaretag.room_db

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
    fun getClientById(clientId: String): Flow<Client>
}

