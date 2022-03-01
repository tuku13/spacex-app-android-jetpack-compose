package hu.tuku13.spacex_compose.datasource

import hu.tuku13.spacex_compose.data.network.SpaceXApi
import hu.tuku13.spacex_compose.util.NetworkErrorResult
import hu.tuku13.spacex_compose.util.NetworkResponse
import hu.tuku13.spacex_compose.util.NetworkResult
import kotlin.Exception

class LaunchNetworkDataSource(private val api: SpaceXApi) {
    suspend fun getLaunches(): NetworkResponse<Any> {
        try {
            val response = api.getLaunches()

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }

    suspend fun getLaunch(id: String): NetworkResponse<Any> {
        try {
            val response = api.getLaunch(id)

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }

    suspend fun getLatestLaunch(): NetworkResponse<Any> {
        try {
            val response = api.getLatestLaunch()

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }
}