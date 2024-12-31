package com.ourteam.hoohflix.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavbarItem (
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : BottomNavbarItem("Home", "home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Search : BottomNavbarItem("Search", "search", Icons.Filled.Search, Icons.Outlined.Search)
    data object Profile : BottomNavbarItem("Profile", "profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
}