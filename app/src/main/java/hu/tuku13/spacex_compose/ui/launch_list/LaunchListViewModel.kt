package hu.tuku13.spacex_compose.ui.launch_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.spacex_compose.data.network.Launch
import hu.tuku13.spacex_compose.repository.SpaceXRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel() {

    private val _launches: MutableLiveData<List<Launch>?> = MutableLiveData()
    val launches: LiveData<List<Launch>?>
        get() = _launches

    private val _navigateToLaunchDetails = MutableLiveData<Launch?>()
    val navigateToLaunchDetails
        get() = _navigateToLaunchDetails

    fun selectLaunch(launch: Launch) {
        _navigateToLaunchDetails.value = launch
    }

    fun doneNavigating() {
        _navigateToLaunchDetails.value = null
    }

    fun getLaunches() {
        _launches.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val launches = repository.getLaunches()
            _launches.postValue(launches)
        }
    }
}