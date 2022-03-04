package hu.tuku13.spacex_compose.ui.launch_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Web
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.spacex_compose.ui.navigation.Routes
import hu.tuku13.spacex_compose.ui.theme.blue
import hu.tuku13.spacex_compose.ui.theme.green
import hu.tuku13.spacex_compose.ui.theme.red

@Composable
fun LaunchDetailsScreen(
    navController: NavController,
    launchId: String,
) {
    val viewModel: LaunchDetailsViewModel = hiltViewModel()

    val launch = viewModel.launch.observeAsState()
    val launchPad = viewModel.launchPad.observeAsState()
    val rocket = viewModel.rocket.observeAsState()
    val crew = viewModel.crew.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getLaunch(launchId)
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            launch.value?.let {
                DataText(
                    title = "Mission name",
                    data = it.name
                )
            }
            rocket.value?.let {
                DataText(
                    title = "Rocket",
                    data = it.name
                )
                Box(modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                    .clickable {
                        navController.navigate("${Routes.ROCKET_DETAILS}/${it.id}")
                    }
                ) {
                    GlideImage(
                        imageModel = it.flickr_images[0]
                    )
                }
            }
            launchPad.value?.let {
                DataText(
                    title = "Launch Site",
                    data = it.full_name
                )
            }
            launch.value?.let {
                DataText(
                    title = "Launch Details",
                    data = it.details ?: "-"
                )

                Column(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "Status",
                        fontSize = 18.sp
                    )
                    when {
                        it.upcoming -> {
                            Text(
                                text = "Upcoming",
                                color = blue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        it.success == true -> {
                            Text(
                                text = "Successful",
                                color = green,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        else -> {
                            Text(
                                text = "Failure",
                                color = red,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            if(crew.value != null)
            Column(
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Crew",
                    fontSize = 18.sp
                )
                LazyRow {
                    items(crew.value!!.size) { index ->
                        val member = crew.value!![index]
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(420.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Box(modifier = Modifier.height(400.dp)) {
                                GlideImage(
                                    imageModel = member.image,
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                            Text(
                                text = member.name,
                            )
                        }
                    }
                }
            }
            
            // Media
            if(
                launch.value?.links?.reddit?.launch != null ||
                launch.value?.links?.article != null ||
                launch.value?.links?.webcast != null
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "Media",
                        fontSize = 18.sp
                    )
                    launch.value?.links?.reddit?.launch?.let {
                        MediaRow(
                            icon = Icons.Default.OndemandVideo,
                            url = it
                        )
                    }
                    launch.value?.links?.article?.let {
                        MediaRow(
                            icon = Icons.Default.Web,
                            url = it
                        )
                    }
                    launch.value?.links?.webcast?.let {
                        MediaRow(
                            icon = Icons.Default.Article,
                            url = it
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun DataText(
    title: String,
    data: String,
) {
    Column(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp
        )
        Text(
            text = data,
        )
    }
}

@Composable
fun MediaRow(
    icon: ImageVector,
    url: String,
){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .height(64.dp)
            .width(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Media logo",
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(64.dp)
                .padding(start = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = url,
                fontSize = 12.sp
            )
        }
    }
}