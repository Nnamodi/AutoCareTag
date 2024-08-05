package dev.borisochieng.autocaretag.di

import android.content.Context
import dev.borisochieng.autocaretag.nfcreader.repository.NFCReaderRepository
import dev.borisochieng.autocaretag.nfcreader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.nfcwriter.data.NfcWriter
import dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.manage.ClientScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

	val appModule = module {
		factory { NFCReaderRepository() }
		factory { NfcWriter() }
		viewModel { AddInfoViewModel() }
		viewModel { NFCReaderViewModel() }
		viewModel { NFCReaderViewModel()}
		viewModel { ClientScreenViewModel() }
	}

}