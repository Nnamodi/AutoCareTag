package dev.borisochieng.autocaretag.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.InfoScreenEvents

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true) val clientId: Long = 0,
    val name: String,
    val contactInfo: String,
    val model: String,
    val lastMaintained: String,
    val nextAppointmentDate: String,
    val note: String,
)

class InvalidClientException(message: String) : Exception(message)

