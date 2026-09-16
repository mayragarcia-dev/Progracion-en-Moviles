package com.garcia.lab04manejoestados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.garcia.lab04manejoestados.ui.theme.Lab04ManejoEstadosTheme

data class Tarea(
    val id: Int,
    val nombre: String,
    val completada: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab04ManejoEstadosTheme {
                TemperatureDisplay()
            }
        }
    }
}
@Composable
fun TemperatureDisplay() {
    var temperatura by remember { mutableStateOf(20) }

    val colorTemperatura = when {
        temperatura > 30 -> Color.Red
        temperatura < 10 -> Color.Blue
        else -> Color.Black
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Temperatura: $temperatura °C",
            color = colorTemperatura
        )

        Button(
            onClick = {
                temperatura++
            }
        ) {
            Text("Subir")
        }

        Button(
            onClick = {
                temperatura--
            }
        ) {
            Text("Bajar")
        }

        Button(
            onClick = {
                temperatura = 20
            }
        ) {
            Text("Resetear")
        }
    }
}