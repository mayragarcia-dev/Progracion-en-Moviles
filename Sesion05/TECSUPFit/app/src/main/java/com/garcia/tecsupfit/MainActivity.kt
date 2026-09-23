package com.garcia.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.garcia.tecsupfit.ui.theme.TECSUPFitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TECSUPFitTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    InicioScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun InicioScreen(
    modifier: Modifier = Modifier
) {
    val clases = listOf(
        Clase(1, "Yoga", "08:00 AM"),
        Clase(2, "Spinning", "10:00 AM"),
        Clase(3, "Funcional", "06:00 PM")
    )

    Text(
        text = "TECSUP Fit - ${clases.size} clases disponibles",
        modifier = modifier
    )
}