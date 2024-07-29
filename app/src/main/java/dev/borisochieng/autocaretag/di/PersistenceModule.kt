package dev.borisochieng.autocaretag.di

import android.content.Context
import androidx.room.Room
import dev.borisochieng.autocaretag.room_db.AutoCareTagDatabase
import dev.borisochieng.autocaretag.room_db.ClientDao
import dev.borisochieng.autocaretag.room_db.RepairDao
import dev.borisochieng.autocaretag.room_db.VehicleDao
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import dev.borisochieng.autocaretag.room_db.repository.ClientRepositoryImpl
import dev.borisochieng.autocaretag.room_db.repository.RepairRepository
import dev.borisochieng.autocaretag.room_db.repository.RepairRepositoryImpl
import dev.borisochieng.autocaretag.room_db.repository.VehicleRepository
import dev.borisochieng.autocaretag.room_db.repository.VehicleRepositoryImpl
import org.koin.dsl.module

object PersistenceModule {

	private fun provideAppDatabase(context: Context): AutoCareTagDatabase {
		return Room.databaseBuilder(
			context = context,
			klass = AutoCareTagDatabase::class.java,
			name = "auto_care_tag_database"
		)
			.fallbackToDestructiveMigration()
			.build()
	}

	private fun provideClientDao(autoCareTagDatabase: AutoCareTagDatabase): ClientDao {
		return autoCareTagDatabase.clientDao()
	}

	private fun provideRepairDao(autoCareTagDatabase: AutoCareTagDatabase): RepairDao {
		return autoCareTagDatabase.repairDao()
	}

	private fun provideVehicleDao(autoCareTagDatabase: AutoCareTagDatabase): VehicleDao {
		return autoCareTagDatabase.vehicleDao()
	}

	val persistenceModule = module {
		single { provideAppDatabase(get<Context>().applicationContext) }
		single { provideClientDao(get()) }
		single { provideRepairDao(get()) }
		single { provideVehicleDao(get()) }
		factory<ClientRepository> { ClientRepositoryImpl() }
		factory<RepairRepository> { RepairRepositoryImpl() }
		factory<VehicleRepository> { VehicleRepositoryImpl() }
	}

}