package com.ourteam.hoohflix.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ourteam.hoohflix.ui.components.BottomNavigationBar
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ourteam.hoohflix.R
import com.ourteam.hoohflix.api.RetrofitInstance
import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.repository.MovieRepository
import com.ourteam.hoohflix.ui.components.ImageSlider
import com.ourteam.hoohflix.ui.components.MovieSection
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavController) {
    LayoutScreen(navController = navController) { snackbarHostState ->
        val scrollState = rememberScrollState()
        val service = RetrofitInstance.movieService
        val popularMovies = remember { mutableStateOf<List<MovieItem>>(emptyList()) }
        val topRatedMovies = remember { mutableStateOf<List<MovieItem>>(emptyList()) }

        LaunchedEffect(Unit) {
            var hasError = false

            try {
                val popularResponse = service.getPopularMovies()
                popularMovies.value = popularResponse.results
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error $e.message")
                hasError = true
            }

            try {
                val topRatedResponse = service.getTopRatedMovies()
                topRatedMovies.value = topRatedResponse.results
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error $e.message")
                hasError = true
            }

            if (hasError) {
                snackbarHostState?.showSnackbar("Error when fetching data")
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            // logo tmdb
            Image(
                painter = painterResource(id = R.drawable.tmdb),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.size(15.dp))

            // image slider
            ImageSlider()

            Spacer(modifier = Modifier.size(15.dp))

            MovieSection(
                title = "Popular Now",
                listMovies = popularMovies.value,
                navController = navController
            )

            MovieSection(
                title = "Top Rated",
                listMovies = topRatedMovies.value,
                navController = navController
            )

        }

    }
}