package hu.tuku13.spacex_compose.ui.rocket_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tuku13.spacex_compose.ui.navigation.Routes

@Composable
fun RocketListScreen(
    navController: NavController,
    viewModel: RocketListViewModel = hiltViewModel()
) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
        Text(text = Routes.ROCKET_LIST_SCREEN)
    }
}