package dev.borisochieng.autocaretag.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.InfoScreenEvents

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true) val clientId: Long = 0,
    val name: String = "",
    val contactInfo: String = "",
    val model: String = "",
    val lastMaintained: Long =0L,
    val nextAppointmentDate: Long =0L,
    val note: String = "",
)


