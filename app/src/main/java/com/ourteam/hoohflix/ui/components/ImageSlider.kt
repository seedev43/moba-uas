package com.ourteam.hoohflix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.ourteam.hoohflix.ui.theme.ThirdColor
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import android.util.Log
import com.ourteam.hoohflix.data.recentMoviesData
import com.ourteam.hoohflix.repository.MovieRepository

@ExperimentalPagerApi
@Composable
fun ImageSlider() {
    val recentMovies = recentMoviesData()
    val imageSlider = recentMovies.map { painterResource(id = it.poster_local) }
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect("imageSlider") {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % imageSlider.size
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        scaleX = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        scaleY = scaleX
                        alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {
                Box {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = recentMovies[page].title,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = recentMovies[page].genre!!,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = recentMovies[page].vote_average.toString(),
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
                        }
                    }
                }
            }
        }

        // Indikator Pager
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            activeColor = Color.White,
            inactiveColor = Color.DarkGray
        )
    }
}