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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.garcia.tecsupfit.ui.theme.TECSUPFitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TECSUPFitTheme {

                val navController = rememberNavController()

                val backStackEntry by navController
                    .currentBackStackEntryAsState()

                val currentRoute = backStackEntry
                    ?.destination
                    ?.route

                val rutasBottomBar = listOf(
                    "inicio",
                    "reservas",
                    "rutinas",
                    "perfil"
                )

                Scaffold(
                    bottomBar = {
                        if (currentRoute in rutasBottomBar) {
                            BottomBar(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                        modifier = Modifier.padding(innerPadding)
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

                        composable("confirmacion/{id}") { backStackEntry ->

                            val id = backStackEntry.arguments
                                ?.getString("id")
                                ?.toIntOrNull()

                            if (id != null) {
                                ConfirmacionScreen(
                                    id = id,
                                    navController = navController
                                )
                            }
                        }

                        composable("reservas") {
                            ReservasScreen(navController)
                        }

                        composable("rutinas") {
                            RutinasScreen()
                        }

                        composable("perfil") {
                            PerfilScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {

    val items = listOf(
        Triple("inicio", "Inicio", Icons.Default.Home),
        Triple("reservas", "Reservas", Icons.Default.CalendarMonth),
        Triple("rutinas", "Rutinas", Icons.Default.FitnessCenter),
        Triple("perfil", "Perfil", Icons.Default.Person)
    )

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.first,

                onClick = {

                    if (currentRoute != item.first) {

                        navController.navigate(item.first) {

                            popUpTo("inicio") {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },

                icon = {
                    Icon(
                        imageVector = item.third,
                        contentDescription = item.second
                    )
                },

                label = {
                    Text(item.second)
                }
            )
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
                    navController.navigate(
                        "confirmacion/${clase.id}"
                    )
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

@Composable
fun ConfirmacionScreen(
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
            text = "Reserva confirmada"
        )

        if (clase != null) {

            Text(
                text = "Clase: ${clase.nombre}"
            )

            Text(
                text = "Horario: ${clase.horario}"
            )

            Button(
                onClick = {
                    navController.navigate("reservas")
                }
            ) {
                Text("Ver reservas")
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

@Composable
fun ReservasScreen(navController: NavController) {

    val reservas = listOf(
        Clase(1, "Yoga", "08:00 AM"),
        Clase(2, "Spinning", "10:00 AM"),
        Clase(3, "Funcional", "06:00 PM")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis reservas"
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {

            items(reservas) { reserva ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Text(
                            text = reserva.nombre
                        )

                        Text(
                            text = reserva.horario
                        )

                        Text(
                            text = "Estado: Confirmada"
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun RutinasScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Rutinas"
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Rutina de fuerza")

                Text("Entrenamiento para mejorar resistencia y fuerza.")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Rutina de cardio")

                Text("Ejercicios para mejorar la resistencia cardiovascular.")
            }
        }
    }
}

@Composable
fun PerfilScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Mi perfil"
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text("Usuario TECSUP")

                Text("usuario@tecsup.edu.pe")
            }
        }

        Text("Estadísticas")

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text("Clases tomadas: 12")

                Text("Racha de asistencia: 5 días")
            }
        }
    }
}