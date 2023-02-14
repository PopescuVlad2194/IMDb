package ro.vlad.core.util

sealed class RequestStateMultipleResults {
    data class Success<T>(val data: List<T>): RequestStateMultipleResults()
    data class Error(val message: String): RequestStateMultipleResults()
    object Empty: RequestStateMultipleResults()
}

sealed class RequestStateSingleResult {
    data class Success<T>(val data: T): RequestStateSingleResult()
    data class Error(val message: String): RequestStateSingleResult()
    object Empty: RequestStateSingleResult()
}
