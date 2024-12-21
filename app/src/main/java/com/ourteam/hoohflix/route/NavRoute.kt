package com.ourteam.hoohflix.route

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ourteam.hoohflix.ui.view.DetailScreen
import com.ourteam.hoohflix.ui.view.HomeScreen
import com.ourteam.hoohflix.ui.view.SearchScreen
import com.ourteam.hoohflix.ui.view.WelcomeScreen
import com.ourteam.hoohflix.ui.view.LoginPage
import com.ourteam.hoohflix.ui.view.SignUpPage

@ExperimentalPagerApi
@ExperimentalMaterial3Api
@Composable
fun NavRoute(navController: NavHostController) {
    NavHost(navController, startDestination = "search") {
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }
        composable("login") {
            LoginPage(navController = navController)
        }
        composable("signup") {
            SignUpPage(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable(
            route = "detail/{movieId}",
            // memastikan movieId bertipe data integer
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(movieId, navController)
        }
    }
}