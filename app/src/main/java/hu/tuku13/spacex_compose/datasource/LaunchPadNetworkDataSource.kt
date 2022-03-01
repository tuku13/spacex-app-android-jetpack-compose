package hu.tuku13.spacex_compose.datasource

import android.util.Log
import hu.tuku13.spacex_compose.data.network.SpaceXApi
import hu.tuku13.spacex_compose.util.NetworkErrorResult
import hu.tuku13.spacex_compose.util.NetworkResponse
import hu.tuku13.spacex_compose.util.NetworkResult


class LaunchPadNetworkDataSource(private val api: SpaceXApi) {
    suspend fun getLaunchpad(id: String): NetworkResponse<Any> {
        try {
            val response = api.getLaunchPad(id)

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            Log.d("DataSource", e.toString())
            return NetworkErrorResult(e)
        }
    }
}