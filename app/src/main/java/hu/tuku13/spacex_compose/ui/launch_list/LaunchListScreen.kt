package hu.tuku13.spacex_compose.ui.launch_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import hu.tuku13.spacex_compose.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.spacex_compose.R
import hu.tuku13.spacex_compose.data.network.Launch
import hu.tuku13.spacex_compose.ui.navigation.Routes.LAUNCH_DETAILS

@Composable
fun LaunchListScreen(
    navController: NavController,
    viewModel: LaunchListViewModel = hiltViewModel()
) {
    val launches by viewModel.launches.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getLaunches()
    }
    
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn{
            items(
                count = launches?.size ?: 0,
            ) { index ->
                val launch = launches?.get(index)
                if (launch != null) {
                    LaunchItem(
                        launch = launch,
                        onItemClick = {
                            navController.navigate("$LAUNCH_DETAILS/${it.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchItem(
    launch: Launch,
    onItemClick: (Launch) -> Unit
){
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .width(64.dp)
                .padding(start = 10.dp)
        ) {
            GlideImage(
                imageModel = launch.links.patch.small ?: "",
                placeHolder = ImageBitmap.imageResource(id = R.drawable.spacex_logo),
                error = ImageBitmap.imageResource(id = R.drawable.spacex_logo)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = launch.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1
            )
            Text(
                text = launch.date_utc,
                fontSize = 16.sp
                )
            when {
                launch.upcoming -> {
                    Text(
                        text = "Upcoming",
                        color = blue,
                        fontSize = 16.sp
                    )
                }
                launch.success == true -> {
                    Text(
                        text = "Successful",
                        color = green,
                        fontSize = 16.sp
                    )
                }
                else -> {
                    Text(
                        text = "Failure",
                        color = red,
                        fontSize = 16.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .height(64.dp)
                .width(32.dp)
                .clickable {
                    onItemClick(launch)
                },
            contentAlignment = Alignment.CenterStart,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Go to launch's details"
            )
        }
    }
}