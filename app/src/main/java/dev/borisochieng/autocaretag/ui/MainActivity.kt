package dev.borisochieng.autocaretag.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import dev.borisochieng.autocaretag.nfc_reader.ui.NFCReaderViewModel
import dev.borisochieng.autocaretag.ui.commons.NavBar
import dev.borisochieng.autocaretag.ui.navigation.AppRoute
import dev.borisochieng.autocaretag.ui.navigation.NavActions
import dev.borisochieng.autocaretag.ui.screens.Client
import dev.borisochieng.autocaretag.ui.screens.HomeScreen
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val nfcReaderViewModel: NFCReaderViewModel by inject()
    private var nfcAdapter: NfcAdapter? = null

    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFilters: Array<IntentFilter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        enableEdgeToEdge()
        setContent {
            AutoCareTagTheme {
                val fakeClients = listOf(
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "Mark Joe",
                        vehicleName = "Ford Ranger"
                    ),
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )
                    ,
                    Client(
                        name = "Sarah",
                        vehicleName = "Benz E200"
                    ),
                    Client(
                        name = "John Doe",
                        vehicleName = "Benz E200"
                    )

                )
                HomeScreen(
                    onNavigateToScan = { startNfcScanning() },
                    onNavigateToNotifications = { /*TODO*/ },
                    onNavigateToClient = {},
                    clients = emptyList()
                )
            }
            val navController = rememberNavController()

            AutoCareTagTheme {
                Scaffold(bottomBar = { NavBar(navController) }) { paddingValues ->
                    AppRoute(
                        navActions = NavActions(navController),
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
        pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )

        intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        )
    }

    private fun startNfcScanning() {
        // Enable foreground dispatch to handle NFC intents
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
        Toast.makeText(this, "NFC scanning started", Toast.LENGTH_SHORT).show()
    }

    private fun stopNfcScanning() {
        // Disable foreground dispatch to stop handling NFC intents
        nfcAdapter?.disableForegroundDispatch(this)
        Toast.makeText(this, "NFC scanning stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action != NfcAdapter.ACTION_NDEF_DISCOVERED) return
        nfcReaderViewModel.readNFCTag(intent)
        Toast.makeText(this, "Tag detected", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent
            .getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        )
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }
}