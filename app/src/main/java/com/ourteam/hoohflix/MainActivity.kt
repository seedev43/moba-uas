package com.ourteam.hoohflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ourteam.hoohflix.api.DjangoRetrofitClient
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.route.NavRoute
import com.ourteam.hoohflix.ui.theme.HooHflixTheme
import com.ourteam.hoohflix.utils.SessionManager

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inisialisasi SessionManager
        val sessionManager = SessionManager(this)
        DjangoRetrofitClient.sessionManager = sessionManager

        setContent {
            HooHflixTheme(darkTheme = false, dynamicColor = false) {
                val navController = rememberNavController()
                NavRoute(navController = navController, sessionManager = sessionManager)
            }
        }
    }
}