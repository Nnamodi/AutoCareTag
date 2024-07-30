package dev.borisochieng.autocaretag.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true) val clientId: Long = 0,
    val name: String,
    val contactInfo: String,
    @ColumnInfo(defaultValue = "") val note: String
)

@Entity
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val vehicleId: Long = 0,
    val make: String,
    val model: String,
    val year: Int,
    val licensePlate: String,
    val vin: String,
    val clientId: Long // Foreign key linking to Client
)

@Entity
data class Repair(
    @PrimaryKey(autoGenerate = true) val repairId: Long = 0,
    val date: Long, // Timestamp
    val description: String,
    val partsReplaced: String,
    val laborCost: Double,
    val partsCost: Double,
    val totalCost: Double,
    val mechanicNotes: String,
    val vehicleId: Long // Foreign key linking to Vehicle
)

