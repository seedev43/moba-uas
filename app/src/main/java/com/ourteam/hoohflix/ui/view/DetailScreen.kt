package com.ourteam.hoohflix.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ourteam.hoohflix.api.RetrofitInstance
import com.ourteam.hoohflix.model.MovieDetail
import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.ui.theme.MainColor
import com.ourteam.hoohflix.ui.theme.ThirdColor

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(movieId: Int, navController: NavController) {
    LayoutScreen(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Back",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // kembali ke home screen
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {},
        navController = navController
    ) {
        val scrollState = rememberScrollState()
        var rating by remember { mutableStateOf(0) }
        val service = RetrofitInstance.movieService
        val detailMovie = remember { mutableStateOf<MovieDetail?>(null) }

        LaunchedEffect(Unit) {
            val detailResponse = service.getMovieDetail(movieId)
            detailMovie.value = detailResponse
        }

        if (detailMovie.value == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            detailMovie.value?.let { detail ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${detail.poster_path}"),
                        contentDescription = "Detail Film",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                                    clip = true
                                )
                                .background(color = MainColor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .verticalScroll(scrollState)
                            ) {
                                Text(
                                    text = detail.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 18.dp)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = detail.vote_average.toString(),
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "Rating",
                                        tint = ThirdColor,
                                        modifier = Modifier.padding(start = 4.dp).size(16.dp)
                                    )
                                }

                                Text(
                                    text = detail.genres.joinToString { it.name },
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Description",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = detail.overview,
                                    fontSize = 13.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Rating This Film",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    (1..5).forEach { index ->
                                        IconButton(
                                            onClick = { rating = index }
                                        ) {
                                            Icon(
                                                imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                                                contentDescription = "Rate $index stars",
                                                tint = ThirdColor,
                                                modifier = Modifier
                                                    .size(30.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "You rated: $rating/5",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}