package ro.vlad.core.util

sealed class ErrorCode(val name: String) {
    object Network : ErrorCode("NetworkError")
    object Internet : ErrorCode("InternetError")
    object Unknown: ErrorCode("UnknownError")
}
