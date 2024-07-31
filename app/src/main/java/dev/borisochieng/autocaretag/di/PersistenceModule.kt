package dev.borisochieng.autocaretag.di

import android.content.Context
import androidx.room.Room
import dev.borisochieng.autocaretag.room_db.AutoCareTagDatabase
import dev.borisochieng.autocaretag.room_db.ClientDao
import dev.borisochieng.autocaretag.room_db.repository.ClientRepository
import dev.borisochieng.autocaretag.room_db.repository.ClientRepositoryImpl
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


	val persistenceModule = module {
		single { provideAppDatabase(get<Context>().applicationContext) }
		single { provideClientDao(get()) }
		factory<ClientRepository> { ClientRepositoryImpl() }

	}

}