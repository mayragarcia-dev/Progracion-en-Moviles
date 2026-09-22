package com.tuapp.navlab.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.navlab.navigation.Screen

@Composable
fun ListScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Lista de elementos")

        Button(
            onClick = {
                navController.navigate(Screen.Detail.createRoute(1))
            }
        ) {
            Text("Ver detalle")
        }

        Button(
            onClick = {
                navController.navigate(Screen.Profile.route)
            }
        ) {
            Text("Ir al perfil")
        }
    }
}