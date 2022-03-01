package hu.tuku13.spacex_compose.repository

import android.util.Log
import hu.tuku13.spacex_compose.data.network.*
import hu.tuku13.spacex_compose.datasource.*
import hu.tuku13.spacex_compose.util.NetworkErrorResult
import hu.tuku13.spacex_compose.util.NetworkResult
import javax.inject.Inject

class SpaceXRepository @Inject constructor(
    private val api: SpaceXApi
) {

    private val launchNetworkDataSource = LaunchNetworkDataSource(api)
    private val rocketNetworkDataSource = RocketNetworkDataSource(api)
    private val roadsterNetworkDataSource = RoadsterNetworkDataSource(api)
    private val launchPadNetworkDataSource = LaunchPadNetworkDataSource(api)
    private val crewNetworkDataSource = CrewNetworkDataSource(api)

    suspend fun getLaunches(): List<Launch>? {
        return when (val response = launchNetworkDataSource.getLaunches()) {
            is NetworkResult -> {
                (response.result as List<Launch>).reversed()
            }
            is NetworkErrorResult -> {
                Log.d("getLaunches", response.exception.toString())
                null
            }
        }
    }

    suspend fun getLaunch(id: String): Launch? {
        return when (val response = launchNetworkDataSource.getLaunch(id)) {
            is NetworkResult -> {
                response.result as Launch
            }
            is NetworkErrorResult -> {
                Log.d("Repository", "Network Error")
                null
            }
        }
    }

    suspend fun getLatestLaunch(): Launch? {
        return when (val response = launchNetworkDataSource.getLatestLaunch()) {
            is NetworkResult -> {
                response.result as Launch
            }
            is NetworkErrorResult -> {
                Log.d("Repository", "Network Error")
                null
            }
        }
    }

    suspend fun getRockets(): List<Rocket>? {
        return when (val response = rocketNetworkDataSource.getRockets()) {
            is NetworkResult -> {
                response.result as List<Rocket>
            }
            is NetworkErrorResult -> {
                Log.d("Repository.getRockets()", "Network Error")
                Log.d("Response", response.toString())
                null
            }
        }
    }

    suspend fun getRocket(id: String): Rocket? {
        return when (val response = rocketNetworkDataSource.getRocket(id)) {
            is NetworkResult -> {
                response.result as Rocket
            }
            is NetworkErrorResult -> {
                Log.d("Repository", "Network Error")
                null
            }
        }
    }

    suspend fun getRoadster(): Roadster? {
        return when (val response = roadsterNetworkDataSource.getRoadster()) {
            is NetworkResult -> {
                response.result as Roadster
            }
            is NetworkErrorResult -> {
                Log.d("Repository", "Network Error")
                null
            }
        }
    }

    suspend fun getLaunchPad(id: String): LaunchPad? {
        return when (val response = launchPadNetworkDataSource.getLaunchpad(id)) {
            is NetworkResult -> {
                response.result as LaunchPad
            }
            is NetworkErrorResult -> {
                Log.d("Repository", "Network Error")
                null
            }
        }
    }

    suspend fun getCrewMember(id: String): CrewMember? {
        return when (val response = crewNetworkDataSource.getCrewMember(id)) {
            is NetworkResult -> {
                response.result as CrewMember
            }
            is NetworkErrorResult -> {
                Log.d("getCrewMember", response.exception.message.toString())
                null
            }
        }
    }
}