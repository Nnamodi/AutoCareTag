package dev.borisochieng.autocaretag.room_db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@DeleteTable.Entries(
    DeleteTable(tableName = "Vehicle"),
    DeleteTable(tableName = "Repair")  // Add more tables here if needed
)
class DeleteTables : AutoMigrationSpec


@Database(
    entities = [Client::class],
    version = 3,
    autoMigrations =  [AutoMigration(
        from= 2,  // Update the version number
        to = 3,
        spec = DeleteTables::class
    )]
)

abstract class AutoCareTagDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

}