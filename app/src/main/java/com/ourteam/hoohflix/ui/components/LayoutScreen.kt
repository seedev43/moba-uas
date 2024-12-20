package com.ourteam.hoohflix.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LayoutScreen(
    navController: NavController,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = { BottomNavigationBar(navController = navController)},
    contentScreen: @Composable (SnackbarHostState?) -> Unit
    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = topBar,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = bottomBar
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            contentScreen(snackbarHostState)
        }
    }
}