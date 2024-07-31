package dev.borisochieng.autocaretag.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.InfoScreenEvents

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true) val clientId: Long = 0,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "")  val contactInfo: String,
    @ColumnInfo(defaultValue = "") val model: String,
    @ColumnInfo(defaultValue = "") val lastMaintained: String,
    @ColumnInfo(defaultValue = "")  val nextAppointmentDate: String,
    @ColumnInfo(defaultValue = "")  val note: String,
)

class InvalidClientException(message: String): Exception(message)

