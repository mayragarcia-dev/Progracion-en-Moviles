package com.garcia.clinicasalud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.garcia.clinicasalud.ui.theme.ClinicaSaludTheme

// Data classes
data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double
)

data class Appointment(
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val status: String
)

val sampleDoctors = listOf(
    Doctor(1, "Dr. Juan Pérez", "Cardiología", 4.9),
    Doctor(2, "Dra. María Gómez", "Pediatría", 4.8),
    Doctor(3, "Dr. Carlos Ruiz", "Dermatología", 4.7),
    Doctor(4, "Dra. Ana Torres", "Cardiología", 4.9)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClinicaSaludTheme {
                ClinicaApp()
            }
        }
    }
}

@Composable
fun ClinicaApp() {
    val navController = rememberNavController()
    
    // Shared list of appointments (starts with sample data including a "Completada" one)
    val appointmentsList = remember {
        mutableStateListOf(
            Appointment("Dr. Juan Pérez", "Cardiología", "24/09/2026", "10:00 AM", "Completada")
        )
    }

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            InicioScreen(
                doctors = sampleDoctors,
                onDoctorClick = { doctor ->
                    navController.navigate("perfil/${doctor.id}")
                },
                onMenuClick = {
                    // Drawer implementation in next stage or placeholder
                }
            )
        }

        composable(
            route = "perfil/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
            val doctor = sampleDoctors.find { it.id == doctorId } ?: sampleDoctors[0]

            PerfilMedicoScreen(
                doctor = doctor,
                onBackClick = { navController.popBackStack() },
                onAgendarClick = {
                    navController.navigate("agendar/${doctor.id}")
                }
            )
        }

        composable(
            route = "agendar/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
            val doctor = sampleDoctors.find { it.id == doctorId } ?: sampleDoctors[0]

            AgendarCitaScreen(
                doctor = doctor,
                onBackClick = { navController.popBackStack() },
                onConfirmarCita = { date, time ->
                    // Add new appointment as "Confirmada"
                    appointmentsList.add(
                        Appointment(
                            doctorName = doctor.name,
                            specialty = doctor.specialty,
                            date = date,
                            time = time,
                            status = "Confirmada"
                        )
                    )
                    navController.navigate("confirmacion/${doctor.name}/${doctor.specialty}/$date/$time") {
                        popUpTo("inicio")
                    }
                }
            )
        }

        composable(
            route = "confirmacion/{doctorName}/{specialty}/{date}/{time}",
            arguments = listOf(
                navArgument("doctorName") { type = NavType.StringType },
                navArgument("specialty") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val doctorName = backStackEntry.arguments?.getString("doctorName") ?: ""
            val specialty = backStackEntry.arguments?.getString("specialty") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""

            ConfirmacionScreen(
                doctorName = doctorName,
                specialty = specialty,
                date = date,
                time = time,
                onVolverInicio = {
                    navController.navigate("inicio") {
                        popUpTo("inicio") { inclusive = true }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    doctors: List<Doctor>,
    onDoctorClick: (Doctor) -> Unit,
    onMenuClick: () -> Unit
) {
    val specialties = listOf("Todos", "Cardiología", "Pediatría", "Dermatología")
    var selectedSpecialty by remember { mutableStateOf("Todos") }

    val filteredDoctors = if (selectedSpecialty == "Todos") {
        doctors
    } else {
        doctors.filter { it.specialty == selectedSpecialty }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clínica Salud+") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Especialidades",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(specialties) { specialty ->
                    FilterChip(
                        selected = selectedSpecialty == specialty,
                        onClick = { selectedSpecialty = specialty },
                        label = { Text(specialty) }
                    )
                }
            }

            Text(
                text = "Médicos Disponibles",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredDoctors) { doctor ->
                    DoctorCard(doctor = doctor, onClick = { onDoctorClick(doctor) })
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = doctor.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Especialidad: ${doctor.specialty}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Calificación: ⭐ ${doctor.rating}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(
    doctor: Doctor,
    onBackClick: () -> Unit,
    onAgendarClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del Médico") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Especialidad: ${doctor.specialty}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Calificación: ⭐ ${doctor.rating}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = onAgendarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar cita")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    doctor: Doctor,
    onBackClick: () -> Unit,
    onConfirmarCita: (String, String) -> Unit
) {
    val dates = listOf("25/09/2026", "26/09/2026", "27/09/2026")
    val times = listOf("09:00 AM", "11:00 AM", "03:00 PM")

    var selectedDate by remember { mutableStateOf(dates[0]) }
    var selectedTime by remember { mutableStateOf(times[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Cita") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Médico: ${doctor.name} (${doctor.specialty})",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Seleccione Fecha:",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                dates.forEach { date ->
                    FilterChip(
                        selected = selectedDate == date,
                        onClick = { selectedDate = date },
                        label = { Text(date) }
                    )
                }
            }

            Text(
                text = "Seleccione Hora:",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                times.forEach { time ->
                    FilterChip(
                        selected = selectedTime == time,
                        onClick = { selectedTime = time },
                        label = { Text(time) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onConfirmarCita(selectedDate, selectedTime) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar cita")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(
    doctorName: String,
    specialty: String,
    date: String,
    time: String,
    onVolverInicio: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmación de Cita") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "¡Cita agendada con éxito!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Médico: $doctorName", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Especialidad: $specialty", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Fecha: $date", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Hora: $time", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onVolverInicio,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
