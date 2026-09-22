package com.tuapp.navlab.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tuapp.navlab.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    Button(
        onClick = {
            navController.navigate(Screen.List.route)
        }
    ) {
        Text("Ir a Lista")
    }
}