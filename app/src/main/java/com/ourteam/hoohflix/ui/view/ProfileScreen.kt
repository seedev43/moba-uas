package com.ourteam.hoohflix.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.R
import com.ourteam.hoohflix.api.DjangoRetrofitClient
import com.ourteam.hoohflix.api.DjangoRetrofitClient.sessionManager
import com.ourteam.hoohflix.model.UserDetailResponse
import com.ourteam.hoohflix.ui.theme.SecondColor
import com.ourteam.hoohflix.utils.SessionManager

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavController, sessionManager: SessionManager) {
    val service = DjangoRetrofitClient.djangoService
    val userId = sessionManager.getUserId()
    var userDetail by remember { mutableStateOf<UserDetailResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun handleLogout() {
        sessionManager.clearSession()
        navController.navigate("login") {
            popUpTo("profile") { inclusive = true }
        }
    }


    LaunchedEffect(Unit) {
        try {
            val response = service.getUserDetail(userId)
            if (response.isSuccessful) {
                userDetail = response.body()
                isLoading = false
            } else {
                errorMessage = "Failed to load user details: ${response.code()}"
                isLoading = false
            }
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            isLoading = false
        }
    }

    LayoutScreen(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
        navController = navController
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Text(
                    fontSize = 24.sp,
                    text = "Hi, ${userDetail?.username ?: "User"}!"
                )

                Spacer(modifier = Modifier.height(50.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clickable { },
                    colors = CardDefaults.cardColors(
                        containerColor = SecondColor
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "Profile"
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Edit Profile"
                        )
                    }
                }

//                Spacer(modifier = Modifier.height(50.dp))


                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clickable {
                            handleLogout()
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout"
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Logout"
                        )
                    }
                }
            }
        }
    }
}