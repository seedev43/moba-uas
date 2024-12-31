package com.ourteam.hoohflix.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.ourteam.hoohflix.R

@Composable
fun ErrorDialog(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    // Membuat dialog
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
            Text(text = errorMessage) // Menampilkan pesan error
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}