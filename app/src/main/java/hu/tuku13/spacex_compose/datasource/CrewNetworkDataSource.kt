package hu.tuku13.spacex_compose.datasource

import hu.tuku13.spacex_compose.data.network.SpaceXApi
import hu.tuku13.spacex_compose.util.NetworkErrorResult
import hu.tuku13.spacex_compose.util.NetworkResponse
import hu.tuku13.spacex_compose.util.NetworkResult

class CrewNetworkDataSource(private val api: SpaceXApi) {
    suspend fun getCrewMember(id: String): NetworkResponse<Any> {
        try {
            val response = api.getCrewMember(id)

            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))

        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }
}