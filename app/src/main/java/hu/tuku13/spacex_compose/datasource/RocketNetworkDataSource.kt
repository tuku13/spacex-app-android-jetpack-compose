package hu.tuku13.spacex_compose.datasource

import android.util.Log
import hu.tuku13.spacex_compose.data.network.SpaceXApi
import hu.tuku13.spacex_compose.util.NetworkErrorResult
import hu.tuku13.spacex_compose.util.NetworkResponse
import hu.tuku13.spacex_compose.util.NetworkResult
import kotlin.Exception

class RocketNetworkDataSource(private val api: SpaceXApi) {
    suspend fun getRockets(): NetworkResponse<Any> {
        try {
            val response = api.getRockets()

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))
        } catch (e: Exception) {
            Log.d("Exception", e.toString())
            return NetworkErrorResult(e)
        }
    }

    suspend fun getRocket(id: String): NetworkResponse<Any> {
        try {
            val response = api.getRocket(id)

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }
}