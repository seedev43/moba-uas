package com.ourteam.hoohflix.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ourteam.hoohflix.model.MovieItem

@Composable
fun MovieSection(
    title: String,
    listMovies: List<MovieItem>?,
    navController: NavController,
) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(15.dp)
    )

    if (listMovies.isNullOrEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) {
                ShimmerLoading()
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listMovies.size) { index ->
                val movie = listMovies[index]
                movie.poster_url?.let { posterUrl ->
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .width(120.dp)
                            .clickable(
                                onClick = { navController.navigate("detail/${movie.id}") },
                                indication = LocalIndication.current, // efek ripple
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w300$posterUrl",
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .wrapContentHeight(),
                            maxLines = 4, // Batas maksimal baris teks
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
