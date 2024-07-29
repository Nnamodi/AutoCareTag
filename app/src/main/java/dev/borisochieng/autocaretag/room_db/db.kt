package dev.borisochieng.autocaretag.room_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Client::class,Vehicle::class,Repair::class], version = 1)

abstract class AutoCareTagDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

    abstract fun vehicleDao(): VehicleDao

    abstract fun repairDao(): RepairDao

}