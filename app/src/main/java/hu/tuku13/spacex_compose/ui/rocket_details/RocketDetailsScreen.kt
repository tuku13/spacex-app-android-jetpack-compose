package hu.tuku13.spacex_compose.ui.rocket_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.spacex_compose.ui.theme.green
import hu.tuku13.spacex_compose.ui.theme.red

@Composable
fun RocketDetailsScreen(
    navController: NavController,
    rocketId: String,
    viewModel: RocketDetailsViewModel = hiltViewModel()
) {
    val rocket = viewModel.rocket.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getRocketData(rocketId)
    }
    
    rocket.value?.let {
        Column(
            Modifier
                .padding(
                    top = 10.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Name",
                fontSize = 18.sp
            )
            Text(
                text = it.name,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Status",
                fontSize = 18.sp
            )
            when (it.active) {
                true -> {
                    Text(
                        text = "Active",
                        color = green,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
                else -> {
                    Text(
                        text = "Inactive",
                        color = red,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }

            Text(
                text = "Description",
                fontSize = 18.sp
            )
            Text(
                text = it.description,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Divider(thickness = 1.dp)

            LazyRow {
                items(it.flickr_images.size) { index ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            imageModel = it.flickr_images[index],
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }

            Divider(thickness = 1.dp)

            CategoryDataText(
                categoryName = "Specs",
                rows = listOf(
                    "Height: %.2f m".format(it.height.meters ?: 0.0),
                    "Diameter: %.1f m".format(it.diameter.meters ?: 0.0),
                    "Mass: %d kg".format(it.mass.kg)
                )
            )

            Divider(thickness = 1.dp)

            CategoryDataText(
                categoryName = "Engines",
                rows = listOf(
                    "Engines: %d".format(it.engines.number),
                    "Propellant #1: ${it.engines.propellant_1}",
                    "Propellant #2: ${it.engines.propellant_2}"
                )
            )

            Divider(thickness = 1.dp)

            CategoryDataText(
                categoryName = "Other facts",
                rows = listOf(
                    "First flight: ${it.first_flight}",
                    "Cost per launch: %d$".format(it.cost_per_launch),
                    "Success rate: ${it.success_rate_pct}%"
                )
            )
        }
    }
}

@Composable
fun CategoryDataText(
    categoryName: String,
    rows: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Text(
            text = categoryName,
            fontSize = 20.sp
        )
        rows.forEach {
            Text(
                text = it,
                modifier = Modifier.padding(5.dp)
            )
        }

    }
}