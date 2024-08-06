package dev.borisochieng.autocaretag.nfcwriter.data

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets

class NfcWriter {

    suspend fun writeLaundryInfoToNfcTag(
        tag: Tag,
        clientId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val ndefRecord = NdefRecord.createMime("text/plain", clientId.toByteArray(StandardCharsets.UTF_8))
            val ndefMessage = NdefMessage(arrayOf(ndefRecord))

            try {
                val ndefTag = Ndef.get(tag)
                ndefTag?.let {
                    it.connect()
                    if (ndefMessage.toByteArray().size > it.maxSize) {
                        onError("Data is too large for this NFC tag")
                        return@withContext
                    }
                    if (it.isWritable) {
                        it.writeNdefMessage(ndefMessage)
                        it.close()
                        onSuccess()
                        return@withContext
                    } else {
                        onError("NFC tag is not writable")
                        return@withContext
                    }
                }

                val ndefFormatableTag = NdefFormatable.get(tag)
                ndefFormatableTag?.let {
                    it.connect()
                    it.format(ndefMessage)
                    it.close()
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.message ?: "Failed to write to tag")
            }
        }
    }

}
