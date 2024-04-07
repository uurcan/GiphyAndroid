package com.example.giphy_android.data.remote.response.base

/**
 * Sealed class representing the result of an API operation, which can be a success, an error, or loading state.
 */
sealed class ApiResult<out T> {
    /**
     * Represents a successful API response with the associated data.
     * @param data The data returned from the API call.
     */
    data class Success<T>(val data: T) : ApiResult<T>()

    /**
     * Represents an API response indicating an error with the given message.
     * @param message The error message describing the failure.
     */
    data class Error<out T>(val message: String) : ApiResult<T>()

    /**
     * Represents a loading state while waiting for the API response.
     */
    object Loading : ApiResult<Nothing>()
}
