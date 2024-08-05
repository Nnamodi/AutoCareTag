package dev.borisochieng.autocaretag.nfcwriter.data

import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.google.gson.Gson
import dev.borisochieng.autocaretag.nfcwriter.domain.NfcWriteState
import dev.borisochieng.autocaretag.nfcwriter.domain.TagInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class NfcWriter {

    suspend fun writeLaundryInfoToNfcTag(
        tag: Tag,
        info: TagInfo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        setupNFC: () -> Unit
    ) {
        setupNFC()
        withContext(Dispatchers.IO) {
            val json = Gson().toJson(info)
            val ndefRecord =
                NdefRecord.createMime("application/json", json.toByteArray(StandardCharsets.UTF_8))
            val ndefMessage = NdefMessage(arrayOf(ndefRecord))

            var ndef: Ndef? = null

            try {
                ndef = Ndef.get(tag)
                if (ndef != null) {
                    ndef.connect()
                    if (ndef.isWritable) {
                        if(ndefMessage.toByteArray().size > ndef.maxSize) {
                            onError("Data is too large for this NFC tag")
                            return@withContext
                        }

                        ndef.writeNdefMessage(ndefMessage)
                        onSuccess()
                    } else {
                        onError("NFC tag is not writable")
                    }


                } else {
                    onError("Device does not support NFC")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")

            } finally {
                ndef?.close()
            }
        }
    }

}
