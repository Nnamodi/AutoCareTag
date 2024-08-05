package dev.borisochieng.autocaretag.nfcreader.repository

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import com.google.gson.Gson
import dev.borisochieng.autocaretag.nfcreader.data.State
import dev.borisochieng.autocaretag.nfcwriter.domain.TagInfo

class NFCReaderRepository {

	fun readNFCTag(intent: Intent): State<TagInfo> {
//		val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
		val ndefMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
		ndefMessage?.forEach { message ->
			(message as NdefMessage).records.forEach { record ->
				if (
					record.tnf == NdefRecord.TNF_WELL_KNOWN &&
					record.type.contentEquals(NdefRecord.RTD_TEXT)
				) {
					val payload = record.payload
					val jsonString = String(payload, Charsets.UTF_8)
					val gson = Gson()
					val tagInfo = gson.fromJson(jsonString, TagInfo::class.java)
					return State.Success(tagInfo)
				}
			}
		}
		return State.Error("Unable to read tag")
	}

}