package dev.borisochieng.autocaretag.nfcreader.ui

data class NfcReadState<out T>(
    val status: NfcReadStatus,
    val data: T? = null,
    val errorMessage: String? = null,

    ) {
    companion object {
        // NFC tag writing success
        fun <T> success(data: T): NfcReadState<T> =
            NfcReadState(NfcReadStatus.SUCCESS, data)

        // NFC tag writing error
        fun <T> error(errorMessage: String): NfcReadState<T> =
            NfcReadState(NfcReadStatus.ERROR, errorMessage = errorMessage)

        // NFC tag writing in progress (loading)
        fun <T> loading(): NfcReadState<T> =
            NfcReadState(NfcReadStatus.LOADING)
        fun <T> idle(): NfcReadState<T> = NfcReadState(NfcReadStatus.IDLE)
    }
}

// Enum to represent NFC tag writing status
enum class NfcReadStatus {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE
}