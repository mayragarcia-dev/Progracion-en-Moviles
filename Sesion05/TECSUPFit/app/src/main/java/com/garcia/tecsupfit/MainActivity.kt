package com.garcia.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.garcia.tecsupfit.ui.theme.TECSUPFitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TECSUPFitTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {

                    composable("inicio") {
                        InicioScreen(navController)
                    }

                    composable("detalle/{id}") { backStackEntry ->

                        val id = backStackEntry.arguments
                            ?.getString("id")
                            ?.toIntOrNull()

                        if (id != null) {
                            DetalleScreen(
                                id = id,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InicioScreen(navController: NavController) {

    val clases = listOf(
        Clase(1, "Yoga", "08:00 AM"),
        Clase(2, "Spinning", "10:00 AM"),
        Clase(3, "Funcional", "06:00 PM")
    )

    var filtroSeleccionado by remember {
        mutableStateOf("Hoy")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "TECSUP Fit"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {

            item {

                Button(
                    onClick = {
                        filtroSeleccionado = "Hoy"
                    },
                    enabled = filtroSeleccionado != "Hoy"
                ) {
                    Text("Hoy")
                }
            }

            item {

                Button(
                    onClick = {
                        filtroSeleccionado = "Esta semana"
                    },
                    enabled = filtroSeleccionado != "Esta semana"
                ) {
                    Text("Esta semana")
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(clases) { clase ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                "detalle/${clase.id}"
                            )
                        }
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

@Composable
fun DetalleScreen(
    id: Int,
    navController: NavController
) {

    val clases = listOf(
        Clase(1, "Yoga", "08:00 AM"),
        Clase(2, "Spinning", "10:00 AM"),
        Clase(3, "Funcional", "06:00 PM")
    )

    val clase = clases.find {
        it.id == id
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Detalle de clase"
        )

        if (clase != null) {

            Text(
                text = clase.nombre
            )

            Text(
                text = clase.horario
            )

            Button(
                onClick = {
                    // Se implementará en el siguiente commit
                }
            ) {
                Text("Reservar cupo")
            }

        } else {

            Text(
                text = "Clase no encontrada"
            )
        }

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Volver")
        }
    }
}