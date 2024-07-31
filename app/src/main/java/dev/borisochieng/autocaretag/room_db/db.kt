package dev.borisochieng.autocaretag.room_db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Client::class],
    version = 3,
    autoMigrations = [AutoMigration(2, 3)]
)

abstract class AutoCareTagDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

}