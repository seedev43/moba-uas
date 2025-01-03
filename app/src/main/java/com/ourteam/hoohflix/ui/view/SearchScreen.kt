package com.ourteam.hoohflix.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ourteam.hoohflix.api.MovieRetrofitClient
import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.ui.theme.MainColor
import com.ourteam.hoohflix.ui.theme.ThirdColor
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@Composable
fun SearchScreen(navController: NavController) {
    var search by remember { mutableStateOf(TextFieldValue("")) }
    var movieList by remember { mutableStateOf<List<MovieItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val service = MovieRetrofitClient.movieService
    val keyboardController = LocalSoftwareKeyboardController.current

    LayoutScreen(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
        navController = navController
    ) { snackbarHostState ->
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .clip(CircleShape)
                        .background(MainColor)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = search,
                        onValueChange = {
                            search = it
                        },
                        placeholder = { Text("Search Movie") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF302E46),
                            unfocusedContainerColor = Color(0xFF302E46),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        },
                        trailingIcon = {
                            if (search.text.isNotEmpty()) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        search = TextFieldValue("")
                                        movieList = emptyList()
                                    },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close"
                                )
                            }
                        },
                        // ubah tombol enter di keyboard menjadi search
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()
                                if (search.text.isBlank()) {
                                    Log.e("SearchScreen", "Query cannot be empty")
                                    return@KeyboardActions // Jangan lanjutkan jika query kosong
                                }
                                isLoading = true
                                showResult = false
                                coroutineScope.launch {
                                    try {
                                        val response = service.searchMovie(query = search.text)
                                        Log.d("SearchScreen", "Response: $response")
                                        if (response.results.isNotEmpty()) {
                                            movieList = response.results
                                            showResult = true
                                        } else {
                                            movieList = emptyList()
                                            showResult = false
                                            Log.e("SearchScreen", "No results found")
                                        }
                                    } catch (e: Exception) {
                                        showResult = false
                                        snackbarHostState?.showSnackbar("Error when fetching data")
                                        Log.e(
                                            "SearchScreen",
                                            "Error fetching movies: ${e.localizedMessage}\n${e.stackTraceToString()}"
                                        )
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.size(18.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = ThirdColor
                )
            }

            LazyColumn {
                items(movieList) { movie ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable (
                                onClick = { navController.navigate("detail/${movie.id}") }
                            )

                    ) {
                        Row(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w300${movie.poster_url}",
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(160.dp)
                                    .width(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 5.dp)
                            ) {
                                Text(text = "${movie.title} (${movie.release_date})")
//                                Text(text = "Action, Thriler, Horror, Serem, Abiez, Takut, Nyo")
                            }
                        }
                    }
                }
            }

            if (!showResult && !isLoading) {
                Text(
                    text = "No results found",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }

        }
    }
}
