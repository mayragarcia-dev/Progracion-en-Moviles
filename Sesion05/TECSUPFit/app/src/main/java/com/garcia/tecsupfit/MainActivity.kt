package com.garcia.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

                val backStackEntry by navController.currentBackStackEntryAsState()

                val currentRoute = backStackEntry?.destination?.route

                val rutasBottomBar = listOf(
                    "inicio",
                    "reservas",
                    "rutinas",
                    "perfil",
                )

                Scaffold(
                    bottomBar = {
                        if (currentRoute in rutasBottomBar) {
                            BottomBar(
                                navController = navController,
                                currentRoute = currentRoute,
                            )
                        }
                    },
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                        modifier = Modifier.padding(innerPadding),
                    ) {

                        composable("inicio") {
                            Surface(modifier = Modifier.fillMaxSize()) {
                                InicioScreen(navController)
                            }
                        }

                        composable("detalle/{id}") { entry ->
                            val id = entry.arguments?.getString("id")?.toIntOrNull()
                            id?.let {
                                Surface(modifier = Modifier.fillMaxSize()) {
                                    DetalleScreen(
                                        id = it,
                                        navController = navController,
                                    )
                                }
                            }
                        }

                        composable("confirmacion/{id}") { entry ->
                            val id = entry.arguments?.getString("id")?.toIntOrNull()
                            id?.let {
                                Surface(modifier = Modifier.fillMaxSize()) {
                                    ConfirmacionScreen(
                                        id = it,
                                        navController = navController,
                                    )
                                }
                            }
                        }

                        composable("reservas") {
                            Surface(modifier = Modifier.fillMaxSize()) {
                                ReservasScreen()
                            }
                        }

                        composable("rutinas") {
                            Surface(modifier = Modifier.fillMaxSize()) {
                                RutinasScreen()
                            }
                        }

                        composable("perfil") {
                            Surface(modifier = Modifier.fillMaxSize()) {
                                PerfilScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// LISTA DE DATOS DE CLASES
// ==========================================
val listaClasesDemo = listOf(
    Clase(
        id = 1,
        nombre = "Yoga funcional",
        horario = "7:00 am",
        sala = "Sala 2",
        duracion = "45 min",
        descripcion = "Clase de yoga orientada al estiramiento, respiración y fortalecimiento corporal.",
        cuposDisponibles = 6,
        cuposTotales = 12,
        fecha = "Hoy",
    ),
    Clase(
        id = 2,
        nombre = "Cross Training",
        horario = "6:00 pm",
        sala = "Sala 1",
        duracion = "45 min",
        descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
        cuposDisponibles = 8,
        cuposTotales = 12,
        fecha = "Hoy",
    ),
    Clase(
        id = 3,
        nombre = "Spinning",
        horario = "7:30 pm",
        sala = "Sala 3",
        duracion = "50 min",
        descripcion = "Sesión cardiovascular en bicicleta estática con ritmo dinámico y música motivadora.",
        cuposDisponibles = 4,
        cuposTotales = 15,
        fecha = "Hoy",
    ),
)

// ==========================================
// BOTTOM NAVIGATION BAR
// ==========================================
@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?,
) {
    val items = listOf(
        Triple("inicio", "Inicio", Icons.Default.Home),
        Triple("reservas", "Reservas", Icons.Default.CalendarMonth),
        Triple("rutinas", "Rutinas", Icons.Default.FitnessCenter),
        Triple("perfil", "Perfil", Icons.Default.Person),
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
                        contentDescription = item.second,
                    )
                },
                label = {
                    Text(item.second)
                },
            )
        }
    }
}

// ==========================================
// PANTALLA 1: INICIO
// ==========================================
@Composable
fun InicioScreen(navController: NavController) {

    val opcionesFiltro = listOf("Hoy", "Esta semana")
    var filtroSeleccionado by remember { mutableStateOf("Hoy") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        // Banner superior verde estilo TECSUP Fit
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
            ) {
                Text(
                    text = "TECSUP Fit",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hola, Mayra",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                )
            }
        }

        // Filtros mediante LazyRow y FilterChip
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(opcionesFiltro) { filtro ->
                val seleccionado = (filtroSeleccionado == filtro)
                FilterChip(
                    selected = seleccionado,
                    onClick = { filtroSeleccionado = filtro },
                    label = { Text(filtro) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            }
        }

        // Título de la sección
        Text(
            text = "Clases disponibles",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        // Lista de clases
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(listaClasesDemo) { clase ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("detalle/${clase.id}")
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Contenedor verde claro con ícono de mancuerna
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = "Icono de clase",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = clase.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${clase.horario} · ${clase.sala}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// PANTALLA 2: DETALLE DE CLASE
// ==========================================
@Composable
fun DetalleScreen(
    id: Int,
    navController: NavController,
) {
    val clase = listaClasesDemo.find { it.id == id } ?: listaClasesDemo.first()

    val horariosDisponibles = listOf("08:00 AM", "10:00 AM", "06:00 PM")
    var horarioSeleccionado by remember { mutableStateOf(clase.horario) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Barra superior de retorno
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                    )
                }
                Text(
                    text = "Detalle de clase",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Banner ilustrativo de la clase
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = "Banner clase",
                    modifier = Modifier.size(56.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            Text(
                text = clase.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "${clase.horario} · ${clase.sala} · ${clase.duracion}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = clase.descripcion,
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "Seleccionar horario:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )

            // Selector de horario con FilterChips (Comportamiento RadioButton)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(horariosDisponibles) { horario ->
                    val seleccionado = (horarioSeleccionado == horario)
                    FilterChip(
                        selected = seleccionado,
                        onClick = { horarioSeleccionado = horario },
                        label = { Text(horario) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                }
            }
        }

        // Botón principal de reserva
        Button(
            onClick = {
                navController.navigate("confirmacion/${clase.id}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Text(
                text = "Reservar cupo",
                modifier = Modifier.padding(vertical = 6.dp),
            )
        }
    }
}

// ==========================================
// PANTALLA 3: CONFIRMACIÓN DE RESERVA
// ==========================================
@Composable
fun ConfirmacionScreen(
    id: Int,
    navController: NavController,
) {
    val clase = listaClasesDemo.find { it.id == id } ?: listaClasesDemo.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // Badge de verificación verde
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirmado",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(40.dp),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Cupo reservado!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = clase.nombre,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )

        Text(
            text = "${clase.fecha}, ${clase.horario} · ${clase.sala}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("reservas") {
                    popUpTo("inicio")
                }
            },
            modifier = Modifier.fillMaxWidth(0.7f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            Text("Ver mis reservas")
        }
    }
}

// ==========================================
// PANTALLA 4: MIS RESERVAS
// ==========================================
@Composable
fun ReservasScreen() {

    val reservas = listOf(
        Clase(
            id = 2,
            nombre = "Cross Training",
            horario = "6:00 pm",
            sala = "Sala 1",
            fecha = "Hoy",
            estado = "Confirmada",
        ),
        Clase(
            id = 1,
            nombre = "Yoga funcional",
            horario = "7:00 am",
            sala = "Sala 2",
            fecha = "Ayer",
            estado = "Completada",
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(
            text = "Mis reservas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(reservas) { reserva ->
                val esConfirmada = reserva.estado == "Confirmada"

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                    ) {
                        // Franja verde lateral para destacar reservas activas
                        if (esConfirmada) {
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primary),
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column {
                                Text(
                                    text = reserva.nombre,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "${reserva.fecha}, ${reserva.horario}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }

                            // Chip de estado de reserva
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (esConfirmada) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                            ) {
                                Text(
                                    text = reserva.estado,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (esConfirmada) {
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 4.dp,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// PANTALLA 5: RUTINAS
// ==========================================
@Composable
fun RutinasScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text(
            text = "Rutinas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "Rutina de fuerza",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Entrenamiento para mejorar resistencia muscular y fuerza general.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "Rutina de cardio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Ejercicios dinámicos para optimizar la resistencia cardiovascular.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ==========================================
// PANTALLA 6: MI PERFIL
// ==========================================
@Composable
fun PerfilScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Avatar circular con iniciales MG
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "MG",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Mayra García",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Plan Premium",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // Fila de estadísticas (Clases y Rachas)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "14",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Clases",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "3",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Rachas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
