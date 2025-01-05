package com.ourteam.hoohflix.ui.view

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ourteam.hoohflix.api.DjangoRetrofitClient
import com.ourteam.hoohflix.api.MovieRetrofitClient
import com.ourteam.hoohflix.model.MovieDetail
import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.model.SubmitRatingRequest
import com.ourteam.hoohflix.ui.components.ConfirmActionDialog
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.ui.theme.MainColor
import com.ourteam.hoohflix.ui.theme.SecondColor
import com.ourteam.hoohflix.ui.theme.ThirdColor
import com.ourteam.hoohflix.utils.SessionManager
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(movieId: Int, navController: NavController, sessionManager: SessionManager) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var rating by remember { mutableStateOf(0) }
    val userId = sessionManager.getUserId()
    val service = MovieRetrofitClient.movieService
    val djangoService = DjangoRetrofitClient.djangoService
    val detailMovie = remember { mutableStateOf<MovieDetail?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
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
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {},
        navController = navController
    ) { snackbarHostState ->
        LaunchedEffect(Unit) {
            val detailResponse = service.getMovieDetail(movieId)
            detailMovie.value = detailResponse
        }

        if (detailMovie.value == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = ThirdColor
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
                                    text = "${detail.title}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
                                )

                                if(!detail.original_title.isNullOrBlank()) {
                                    Text(
                                        text = detail.original_title,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(bottom = 18.dp)
                                    )
                                }


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
                                        modifier = Modifier
                                            .padding(start = 4.dp)
                                            .size(16.dp)
                                    )
                                }

                                Text(
                                    text = detail.release_date,
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 0.dp)
                                )

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
                                    modifier = Modifier.padding(top = 8.dp, bottom = 15.dp)
                                )

                                Button(
                                    onClick = { showConfirmationDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = SecondColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(text = "Submit Rating", color = Color.White)
                                }

                                if (showConfirmationDialog) {
                                    ConfirmActionDialog(
                                        title = "Confirm Rating",
                                        description = "Are you sure you want to submit this rating?",
                                        onDismiss = { showConfirmationDialog = false }
                                    ) {
                                        showConfirmationDialog = false
                                        coroutineScope.launch {
                                            try {
                                                val response = djangoService.submitRating(
                                                    SubmitRatingRequest(
                                                        user_id = userId,
                                                        movie_id = movieId.toString(),
                                                        rating = rating,
                                                        genre = detail.genres.joinToString { it.name }
                                                    )
                                                )

                                                if (response.isSuccessful) {
                                                    Toast.makeText(context, "You rating has been submitted", Toast.LENGTH_SHORT).show()
                                                    navController.popBackStack()
                                                } else {
                                                    Toast.makeText(context, "Failed to rating this movie", Toast.LENGTH_SHORT).show()
                                                }
                                            } catch (e: Exception) {
                                                Log.e("DetailScreen", e.toString())
                                                snackbarHostState?.showSnackbar("Please try again and check your internet connection.")
                                            }
                                        }

                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}