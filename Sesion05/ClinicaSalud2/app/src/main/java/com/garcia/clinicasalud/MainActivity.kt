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
import com.garcia.clinicasalud.ui.theme.ClinicaSaludTheme

// Data classes
data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClinicaSaludTheme {
                InicioScreen(
                    onDoctorClick = { doctor ->
                        // Will implement navigation to Doctor Profile in next stage
                    },
                    onMenuClick = {
                        // Will implement drawer in later stage
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    onDoctorClick: (Doctor) -> Unit,
    onMenuClick: () -> Unit
) {
    // Sample data
    val specialties = listOf("Todos", "Cardiología", "Pediatría", "Dermatología")
    val doctors = listOf(
        Doctor(1, "Dr. Juan Pérez", "Cardiología", 4.9),
        Doctor(2, "Dra. María Gómez", "Pediatría", 4.8),
        Doctor(3, "Dr. Carlos Ruiz", "Dermatología", 4.7),
        Doctor(4, "Dra. Ana Torres", "Cardiología", 4.9)
    )

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

            // LazyRow for Specialties (Chips)
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

            // LazyColumn for Doctors
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
