package dev.borisochieng.autocaretag.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Client::class,Vehicle::class,Repair::class], version = 1)

abstract class AutoCareTagDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun repairDao(): RepairDao
    companion object {

        @Volatile
        private var instance: AutoCareTagDatabase? = null

        /**
         * Returns an instance of Room Database.
         *
         * @param context application context
         * @return The singleton LetterDatabase
         */
        fun getInstance(context: Context): AutoCareTagDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    AutoCareTagDatabase::class.java,
                    "auto_care_tag_database"
                )
                    .fallbackToDestructiveMigration() // Add this line if you want to handle migrations by destroying and recreating the database
                    .build().also { instance = it }
                // .build()
            }
        }
    }
}