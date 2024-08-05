package dev.borisochieng.autocaretag.nfcreader.data

sealed class State<out T: Any> {

	data class Success<out T: Any>(val data: T) : State<T>()

	data class Error(val errorMessage: String) : State<Nothing>()

	data object Loading : State<Nothing>()

}