package dev.borisochieng.autocaretag

import android.app.Application
import dev.borisochieng.autocaretag.di.AppModule.appModule
import dev.borisochieng.autocaretag.di.PersistenceModule.persistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AutoCareTagApp : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@AutoCareTagApp)
			androidLogger(Level.INFO)
			modules(appModule, persistenceModule)
		}
	}

}
