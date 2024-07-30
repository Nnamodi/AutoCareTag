package dev.borisochieng.autocaretag.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.ui.commons.NavBar
import dev.borisochieng.autocaretag.ui.navigation.AppRoute
import dev.borisochieng.autocaretag.ui.navigation.NavActions
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val nfcReaderViewModel: NFCReaderViewModel by inject()
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var navActions: NavActions

    private var tag: Tag? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFilters: Array<IntentFilter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navActions = NavActions(navController)

            AutoCareTagTheme {
                Scaffold(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
                    bottomBar = { NavBar(navController) }
                ) { paddingValues ->
                    AppRoute(
                        navActions = navActions,
                        navController = navController,
                        paddingValues = paddingValues,
                        scanNfc = { shouldScan ->
                            if (shouldScan) startNfcScanning() else stopNfcScanning()
                        },
                        tag = tag,
                        setupNfc = { setupNfc() }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action != NfcAdapter.ACTION_NDEF_DISCOVERED) return
        nfcReaderViewModel.readNFCTag(intent)
        Toast.makeText(this, "Tag detected", Toast.LENGTH_LONG).show()

        val screen = if (nfcReaderViewModel.tagIsEmpty) {
            Screens.AddScreen
        } else Screens.ClientDetailsScreen(
            nfcReaderViewModel.clientUiState.client.clientId.toString()
        )
        navActions.navigate(screen)
        Toast.makeText(this, "Tag detected", Toast.LENGTH_LONG).show()

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            // NFC tag discovered
            tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        }

    }

    override fun onResume() {
        super.onResume()
        startNfcScanning(alertUser = false)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun startNfcScanning(alertUser: Boolean = true) {
        // Enable foreground dispatch to handle NFC intents
        val intent = Intent(this, javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        )
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
        if (!alertUser) return
        Toast.makeText(this, "NFC scanning started", Toast.LENGTH_SHORT).show()
    }

    private fun stopNfcScanning() {
        // Disable foreground dispatch to stop handling NFC intents
        nfcAdapter?.disableForegroundDispatch(this)
        Toast.makeText(this, "NFC scanning stopped", Toast.LENGTH_SHORT).show()
    }

    private fun setupNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}