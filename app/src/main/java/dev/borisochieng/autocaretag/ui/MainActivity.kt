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
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfcreader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.ui.commons.NavBar
import dev.borisochieng.autocaretag.ui.navigation.AppRoute
import dev.borisochieng.autocaretag.ui.navigation.NavActions
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val nfcReaderViewModel: NFCReaderViewModel by inject()
    private var nfcAdapter: NfcAdapter? = null

    private val actions = arrayOf(
        NfcAdapter.ACTION_NDEF_DISCOVERED,
        NfcAdapter.ACTION_TAG_DISCOVERED,
        NfcAdapter.ACTION_TECH_DISCOVERED
    )
    private lateinit var navActions: NavActions

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AutoCareTag)
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navActions = NavActions(navController)

            AutoCareTagTheme {
                Scaffold(
                    containerColor = colorScheme.background,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.systemBars),
                    bottomBar = { NavBar(navController) },
                ) { paddingValues ->
                    AppRoute(
                        navActions = navActions,
                        navController = navController,
                        paddingValues = paddingValues,
                        scanNfc = { shouldScan ->
                            if (shouldScan) startNfcScanning() else stopNfcScanning()
                        }
                    )
                }

            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action !in actions) return
        // NFC tag discovered
        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)?.let {
            nfcReaderViewModel.onTagFetched(it)
        }
        nfcReaderViewModel.readNFCTag(intent)
        Toast.makeText(this, "Tag detected", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        nfcReaderViewModel.toggleNfcEnabledStatus(
            enabled = nfcAdapter != null && nfcAdapter?.isEnabled == true
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun startNfcScanning() {
        // Enable foreground dispatch to handle NFC intents
        val intent = Intent(this, javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val intentFilters = actions.map { IntentFilter(it) }.toTypedArray()

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
        Toast.makeText(this, "NFC scanning started", Toast.LENGTH_SHORT).show()
    }

    private fun stopNfcScanning() {
        // Disable foreground dispatch to stop handling NFC intents
        nfcAdapter?.disableForegroundDispatch(this)
        Toast.makeText(this, "NFC scanning stopped", Toast.LENGTH_SHORT).show()
    }
}