package com.ourteam.hoohflix.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ourteam.hoohflix.data.BottomNavbarItems
import com.ourteam.hoohflix.model.BottomNavbarItem


@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavbarItems.forEach { item ->
            val selected = currentRoute == item.route
            val icon = remember(selected) {
                if (selected) item.selectedIcon else item.unselectedIcon
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
