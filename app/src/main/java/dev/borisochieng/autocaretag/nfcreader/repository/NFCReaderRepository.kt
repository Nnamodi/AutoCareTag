package dev.borisochieng.autocaretag.nfcreader.repository

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import dev.borisochieng.autocaretag.nfcreader.data.State

typealias ClientId = String

class NFCReaderRepository {

	fun readNFCTag(intent: Intent): State<ClientId> {
		val ndefMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
		ndefMessage?.forEach { message ->
			val record = (message as NdefMessage).records[0]
			if (
				record.tnf == NdefRecord.TNF_WELL_KNOWN &&
				record.type.contentEquals(NdefRecord.RTD_TEXT)
			) {
				val payload = record.payload
				val tagInfo = String(payload, Charsets.UTF_8)
				return State.Success(tagInfo)
			}
		}
		return State.Error("Unable to read tag")
	}

}