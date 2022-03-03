package hu.tuku13.spacex_compose.ui.rocket_list

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.spacex_compose.data.network.Rocket
import hu.tuku13.spacex_compose.ui.navigation.Routes

@Composable
fun RocketListScreen(
    navController: NavController,
    viewModel: RocketListViewModel = hiltViewModel()
) {
    val rockets = viewModel.rockets.observeAsState()
    LaunchedEffect(Unit){
        viewModel.getRockets()
    }

    LazyColumn{
        rockets.value?.let { rocketList ->
            items(rocketList.size) { index ->
                val rocket = rockets.value!![index]
                ImageWithLabel(
                    imageUrl = rocket.flickr_images[0],
                    label = rocket.name,
                    onClick = { navController.navigate("${Routes.ROCKET_DETAILS}/${rocket.id}") }
                )
            }
        }
    }
}

@Composable
fun ImageWithLabel(
    imageUrl: String,
    label: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .clickable{ onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
        ) {
            GlideImage(imageModel = imageUrl)
        }
        Text(text = label,)
    }
}

