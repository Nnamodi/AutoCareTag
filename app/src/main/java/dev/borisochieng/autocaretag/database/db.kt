package dev.borisochieng.autocaretag.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Client::class],
    version = 1
)

abstract class AutoCareTagDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

//    @DeleteTable("Vehicle")
//    @DeleteTable("Repair")
//    class AutoCareMigrations: AutoMigrationSpec{
//        override fun onPostMigrate(db: SupportSQLiteDatabase) {
//            super.onPostMigrate(db)
//            db.execSQL("DROP TABLE Vehicle")
//            db.execSQL("DROP TABLE Repair")
//            db.execSQL("ALTER TABLE Client ADD COLUMN model VARCHAR NULL")
//            db.execSQL("ALTER TABLE Client ADD COLUMN lastMaintained VARCHAR NULL")
//            db.execSQL("ALTER TABLE Client ADD COLUMN note VARCHAR NULL")
//            db.execSQL("ALTER TABLE Client ADD COLUMN nextAppointmentDate VARCHAR NULL")
//        }
//    }

}