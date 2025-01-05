package com.ourteam.hoohflix.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.ourteam.hoohflix.R
import com.ourteam.hoohflix.model.SubmitRatingRequest
import com.ourteam.hoohflix.ui.theme.SecondColor
import kotlinx.coroutines.launch

@Composable
fun ErrorDialog(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Error", style = TextStyle(fontSize = 18.sp))
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.error),
                contentDescription = "Error icon",
                tint = Color.Red
            )
        },
        text = {
            Text(text = errorMessage)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun ConfirmActionDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onClickEvent: () -> Unit
) {
    AlertDialog(
        containerColor = SecondColor,
        titleContentColor = Color.White,
        textContentColor = Color.White,
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, color = Color.White)
        },
        text = {
            Text(text = description, color = Color.White)
        },
        confirmButton = {
            Button(
                onClick = onClickEvent
            ) {
                Text("Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}