package hu.tuku13.spacex_compose.ui.roadster

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.spacex_compose.ui.launch_details.DataText
import hu.tuku13.spacex_compose.ui.navigation.Routes

@Composable
fun RoadsterScreen(
    navController: NavController,
    viewModel: RoadsterViewModel = hiltViewModel()
) {
    val roadster = viewModel.roadster.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getRoadster()
    }

    roadster.value?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Where is Elon's Roadster?",
                    fontSize = 20.sp
                )
            }
            DataText(
                title = "Name",
                data = it.name
            )
            DataText(
                title = "Details",
                data = it.details
            )

            Text(
                text = "Images",
                fontSize = 18.sp
            )

            LazyRow{
                items(it.flickr_images.size) { index ->
                    Box(modifier = Modifier.padding(20.dp)) {
                        GlideImage(
                            imageModel = it.flickr_images[index]
                        )
                    }
                }
            }

            DataText(
                title = "Launch date",
                data = it.launch_date_utc
            )
            DataText(
                title = "Mass",
                data = "${it.launch_mass_kg} kg"
            )
            DataText(
                title = "Speed",
                data = "%.3f km/h".format(it.speed_kph)
            )
            DataText(
                title = "Distance from Earth",
                data =  "%.3f km".format(it.earth_distance_km)
            )
            DataText(
                title = "Distance from Mars",
                data =  "%.3f km".format(it.mars_distance_km)
            )
            DataText(
                title = "Orbit type",
                data =  "heliocentric"
            )
            DataText(
                title = "Longitude",
                data =  "%.3f".format(it.longitude)
            )
            DataText(
                title = "Apoapsis",
                data =  "%.7f AU".format(it.apoapsis_au)
            )
            DataText(
                title = "Periapsis",
                data =  "%.7f AU".format(it.periapsis_au)
            )
        }
    }

}