package dev.borisochieng.autocaretag.nfc_writer.data

import android.content.Context
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.google.gson.Gson
import dev.borisochieng.autocaretag.nfc_writer.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfc_writer.domain.TagInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NfcWriter(private val context: Context) {
//    private lateinit var nfcAdapter: NfcAdapter
//    private lateinit var pendingIntent: PendingIntent


    fun writeLaundryInfoToNfcTag(
        tag: Tag,
        info: TagInfo
    ): Flow<NfcWriteState<TagInfo>> = flow {
        // Initialize NFC adapter and check if NFC is available
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            // NFC not available on this device
            emit(NfcWriteState.error("NFC not available on this device"))
            return@flow
        }

        val json = Gson().toJson(info)
        val ndefRecord = NdefRecord.createMime("application/json", json.toByteArray())
        val ndefMessage = NdefMessage(arrayOf(ndefRecord))

        try {
            val ndef = Ndef.get(tag)
            ndef.connect()
            ndef.writeNdefMessage(ndefMessage)
            ndef.close()
            // Writing success
            emit(NfcWriteState.success(info))
        } catch (e: Exception) {
            // Writing error
            emit(NfcWriteState.error("Error writing LaundryInfo to NFC tag: ${e.message}"))
        }
    }


//     fun setupNfc() {
//        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
//        val intent = Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//    }

}
