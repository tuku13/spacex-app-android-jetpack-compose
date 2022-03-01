package hu.tuku13.spacex_compose.util

sealed class NetworkResponse<out T: Any>

class NetworkResult<K: Any>(val result: K) : NetworkResponse<K>()

class NetworkErrorResult(val exception: Exception) : NetworkResponse<Nothing>()