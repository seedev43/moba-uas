package com.ourteam.hoohflix.ui.view


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.ourteam.hoohflix.model.RegisterRequest
import com.ourteam.hoohflix.ui.components.ErrorDialog
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.ui.theme.MainColor
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun SignUpPage(navController: NavController) {
    LayoutScreen(topBar = {}, bottomBar = {}, navController = navController) { snackbarHostState ->
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isSubmit by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        var showErrorDialog by remember { mutableStateOf(false) }
        var buttonActive by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        val service = DjangoRetrofitClient.djangoService

        val backgroundImage: Painter = painterResource(id = R.drawable.bg_login)

        buttonActive = firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()

        if(showErrorDialog) {
            ErrorDialog(
                errorMessage = errorMessage,
                onDismiss = { showErrorDialog = false }
            )
        }

        fun handleSignUp() {
            isSubmit = true
            buttonActive = false
            coroutineScope.launch {
                if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                ) {
                    snackbarHostState?.showSnackbar("Please fill in all fields.")
                    isSubmit = false
                    return@launch
                }

                if(password != confirmPassword) {
                    isSubmit = false
                    buttonActive = false
                    errorMessage = "password do not match"
                    showErrorDialog = true
                    return@launch
                }

                try {
                    val body = RegisterRequest(firstName, lastName, username, email, password)
                    var response = service.registerUser(body)

                    if(response.isSuccessful) {
                        isSubmit = false
                        navController.navigate("login")
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
                    Log.e("SignUpScreen", e.toString())
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainColor)
        ){
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
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First name") },
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
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last name") },
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
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
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
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
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
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
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
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm password") },
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
                    onClick = { handleSignUp() },
                    enabled = buttonActive,
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
                        Text("Sign up")
                    }
                }
            }
        }

    }



}
