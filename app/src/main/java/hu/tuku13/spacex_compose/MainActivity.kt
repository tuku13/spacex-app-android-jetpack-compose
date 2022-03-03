package hu.tuku13.spacex_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.spacex_compose.ui.launch_details.LaunchDetailsScreen
import hu.tuku13.spacex_compose.ui.launch_list.LaunchListScreen
import hu.tuku13.spacex_compose.ui.navigation.BottomNavItem
import hu.tuku13.spacex_compose.ui.navigation.BottomNavigationBar
import hu.tuku13.spacex_compose.ui.navigation.Routes.LAUNCH_DETAILS
import hu.tuku13.spacex_compose.ui.navigation.Routes.LAUNCH_LIST_SCREEN
import hu.tuku13.spacex_compose.ui.navigation.Routes.ROADSTER_SCREEN
import hu.tuku13.spacex_compose.ui.navigation.Routes.ROCKET_DETAILS
import hu.tuku13.spacex_compose.ui.navigation.Routes.ROCKET_LIST_SCREEN
import hu.tuku13.spacex_compose.ui.roadster.RoadsterScreen
import hu.tuku13.spacex_compose.ui.rocket_details.RocketDetailsScreen
import hu.tuku13.spacex_compose.ui.rocket_list.RocketListScreen
import hu.tuku13.spacex_compose.ui.theme.Purple700
import hu.tuku13.spacex_compose.ui.theme.SpaceXComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXComposeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("SpaceX app")
                                    },
                            backgroundColor = MaterialTheme.colors.background
                        ) },
                    bottomBar =  {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Launches",
                                    route = LAUNCH_LIST_SCREEN,
                                    icon = Icons.Default.List
                                ),
                                BottomNavItem(
                                    name = "Rockets",
                                    route = ROCKET_LIST_SCREEN,
                                    imgResource = R.drawable.ic_baseline_rocket_launch_24
                                ),
                                BottomNavItem(
                                    name = "Roadster",
                                    route = ROADSTER_SCREEN,
                                    icon = Icons.Default.DirectionsCar
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    },
                    content = { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = LAUNCH_LIST_SCREEN,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(LAUNCH_LIST_SCREEN) {
                                LaunchListScreen(navController = navController)
                            }
                            composable(ROCKET_LIST_SCREEN) {
                                RocketListScreen(navController = navController)
                            }
                            composable(ROADSTER_SCREEN) {
                                RoadsterScreen(navController = navController)
                            }
                            composable(
                                route = "$ROCKET_DETAILS/{rocketId}",
                                arguments = listOf(
                                    navArgument("rocketId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                RocketDetailsScreen(navController = navController)
                            }
                            composable(
                                route = "$LAUNCH_DETAILS/{launchId}",
                                arguments = listOf(
                                    navArgument("launchId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val launchId = it.arguments?.getString("launchId") ?: ""
                                LaunchDetailsScreen(
                                    navController = navController,
                                    launchId = launchId
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
