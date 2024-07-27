package dev.borisochieng.tagtidy

import android.app.Application
import dev.borisochieng.tagtidy.nfc_reader.repository.NFCReaderRepository
import dev.borisochieng.tagtidy.nfc_reader.NFCReaderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class TagTidyApp : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@TagTidyApp)
			androidLogger(Level.INFO)
			modules(appModule)
		}
	}

}

val appModule = module {
	factory { NFCReaderRepository() }
	viewModel { NFCReaderViewModel() }
}
