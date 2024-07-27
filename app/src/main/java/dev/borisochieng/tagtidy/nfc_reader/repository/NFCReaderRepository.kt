package dev.borisochieng.tagtidy.nfc_reader.repository

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import com.google.gson.Gson
import dev.borisochieng.tagtidy.nfc_reader.data.LaundryInfo
import dev.borisochieng.tagtidy.nfc_reader.data.State

class NFCReaderRepository {

	fun readNFCTag(intent: Intent): State<LaundryInfo> {
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
					val laundryInfo = gson.fromJson(jsonString, LaundryInfo::class.java)
					return State.Success(laundryInfo)
				}
			}
		}
		return State.Error("Unable to read tag")
	}

}