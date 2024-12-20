package com.ourteam.hoohflix.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ourteam.hoohflix.ui.components.BottomNavigationBar
import com.ourteam.hoohflix.ui.components.LayoutScreen

@Composable
fun SearchScreen(navController: NavController) {
    LayoutScreen(navController = navController) {
        Text(text = "INI SEARCH SCREEN")
    }
}