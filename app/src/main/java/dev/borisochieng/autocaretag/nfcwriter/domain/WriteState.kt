package dev.borisochieng.autocaretag.nfcwriter.domain

// Wrapper class for managing NFC tag writing state
data class NfcWriteState<out T>(
    val status: NfcWriteStatus,
    val data: T? = null,
    val errorMessage: String? = null,

) {
    companion object {
        // NFC tag writing success
        fun <T> success(data: T): NfcWriteState<T> =
            NfcWriteState(NfcWriteStatus.SUCCESS, data)

        // NFC tag writing error
        fun <T> error(errorMessage: String): NfcWriteState<T> =
            NfcWriteState(NfcWriteStatus.ERROR, errorMessage = errorMessage)

        // NFC tag writing in progress (loading)
        fun <T> loading(): NfcWriteState<T> =
            NfcWriteState(NfcWriteStatus.LOADING)
        fun <T> idle(): NfcWriteState<T> = NfcWriteState(NfcWriteStatus.IDLE)
    }
}

// Enum to represent NFC tag writing status
enum class NfcWriteStatus {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE
}