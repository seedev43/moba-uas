package com.ourteam.hoohflix.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ourteam.hoohflix.R
import com.ourteam.hoohflix.api.DjangoRetrofitClient
import com.ourteam.hoohflix.model.LoginRequest
import com.ourteam.hoohflix.model.RegisterRequest
import com.ourteam.hoohflix.ui.components.ErrorDialog
import com.ourteam.hoohflix.ui.theme.MainColor
import com.ourteam.hoohflix.utils.SessionManager
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun LoginPage(navController: NavController, sessionManager: SessionManager) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSubmit by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var buttonActive by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val service = DjangoRetrofitClient.djangoService

    val backgroundImage: Painter = painterResource(id = R.drawable.bg_login)

    buttonActive = username.isNotEmpty() && password.isNotEmpty()

    if(showErrorDialog) {
        ErrorDialog(
            errorMessage = errorMessage,
            onDismiss = { showErrorDialog = false }
        )
    }

    fun handleLogin() {
        isSubmit = true
        buttonActive = false
        coroutineScope.launch {
            try {
                val body = LoginRequest(username, password)
                var response = service.loginUser(body)

                if(response.isSuccessful) {
                    val responseBody = response.body()
                    val sessionCookie = response.headers()["Set-Cookie"]?.split(";")?.find {
                        it.startsWith("sessionid")
                    }

                    responseBody?.let {
                        if(it.success) {
                            sessionCookie?.let { cookie ->
                                sessionManager.saveSessionCookie(cookie) // Simpan cookie
                            }
                            sessionManager.saveUserId(it.id)

                            isSubmit = false
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }

                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    errorMessage = errorJson.getString("message")
                    showErrorDialog = true
                    isSubmit = false
                    buttonActive = false
                }

            } catch(e: Exception) {
                errorMessage = "Internal Server Error"
                buttonActive = false
                isSubmit = false
                showErrorDialog = true
                Log.e("LoginScreen", e.toString())
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Add the background image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainColor.copy(alpha = 0.5f)) // Optional overlay
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 150.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Enter your username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter your password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                enabled = buttonActive,
                onClick = { handleLogin() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                if (isSubmit) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Login")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate("signup") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Don't have an account? Sign up", color = Color.White)
            }
        }
    }
}
