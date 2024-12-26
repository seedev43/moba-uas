package com.ourteam.hoohflix.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ourteam.hoohflix.ui.components.LayoutScreen
import com.ourteam.hoohflix.ui.theme.MainColor
import com.ourteam.hoohflix.ui.theme.ThirdColor


@ExperimentalMaterial3Api
@Composable
fun SearchScreen(navController: NavController) {
    var textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    LayoutScreen(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
        navController = navController
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            SearchBar(
                modifier = Modifier.padding(top = 0.dp).align(Alignment.TopCenter).offset(y = (-16).dp),
                colors = SearchBarDefaults.colors(
                    containerColor = ThirdColor,
                    dividerColor = MainColor,
//                    inputFieldColors = TextFieldColors(
//
//                    )
                ),
                inputField = {
                    SearchBarDefaults.InputField(
                        state = textFieldState,
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("Search Movie") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if(expanded) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        textFieldState.clearText()
                                    },
                                    imageVector =  Icons.Default.Close,
                                    contentDescription = "Close"
                                )
                            }
                        },
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },

                ) {

            }
        }


    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val navController = rememberNavController() // Create a dummy NavController
    SearchScreen(navController = navController)
}
