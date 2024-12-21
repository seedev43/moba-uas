package com.ourteam.hoohflix.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ourteam.hoohflix.ui.components.LayoutScreen


@ExperimentalMaterial3Api
@Composable
fun SearchScreen(navController: NavController) {
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search movies...") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "INI SEARCH SCREEN")
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val navController = rememberNavController() // Create a dummy NavController
    SearchScreen(navController = navController)
}
