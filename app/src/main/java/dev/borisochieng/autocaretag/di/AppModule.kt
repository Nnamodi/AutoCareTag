package dev.borisochieng.autocaretag.di

import android.content.Context
import dev.borisochieng.autocaretag.nfc_reader.repository.NFCReaderRepository
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.nfc_writer.data.NfcWriter
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.manage.ClientScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

	val appModule = module {
		factory { NFCReaderRepository() }
		factory { NfcWriter(get<Context>().applicationContext) }
		viewModel { AddInfoViewModel() }
		viewModel { NFCReaderViewModel() }
		viewModel { NFCReaderViewModel()}
		viewModel { ClientScreenViewModel() }
	}

}