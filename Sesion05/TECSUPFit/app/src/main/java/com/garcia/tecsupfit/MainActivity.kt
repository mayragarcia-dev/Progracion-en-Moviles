package com.garcia.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    var filtroSeleccionado by remember {
        mutableStateOf("Hoy")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        Text(
            text = "TECSUP Fit",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Button(
                    onClick = {
                        filtroSeleccionado = "Hoy"
                    },
                    modifier = Modifier.padding(start = 16.dp),
                    enabled = filtroSeleccionado != "Hoy"
                ) {
                    Text(
                        text = "Hoy"
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        filtroSeleccionado = "Esta semana"
                    },
                    enabled = filtroSeleccionado != "Esta semana"
                ) {
                    Text(
                        text = "Esta semana"
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(clases) { clase ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = clase.nombre
                        )

                        Text(
                            text = clase.horario
                        )
                    }
                }
            }
        }
    }
}