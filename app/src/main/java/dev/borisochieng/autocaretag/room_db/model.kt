package dev.borisochieng.autocaretag.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey val clientId: String = "",
    val name: String,
    val contactInfo: String,
    val model: String,
    val lastMaintained: String,
    val nextAppointmentDate: String,
    val note: String,
)

class InvalidClientException(message: String) : Exception(message)

