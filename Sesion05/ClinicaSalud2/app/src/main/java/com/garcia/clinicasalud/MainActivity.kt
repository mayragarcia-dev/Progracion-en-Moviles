package com.garcia.clinicasalud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.garcia.clinicasalud.ui.theme.ClinicaSaludTheme
import kotlinx.coroutines.launch

// ==========================================
// DATA MODELS & SAMPLE DATA
// ==========================================

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double,
    val experience: String = "12 años exp.",
    val reviewsCount: Int = 128,
    val description: String = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
)

data class Appointment(
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val status: String // "Confirmada" o "Completada"
)

val sampleDoctors = listOf(
    Doctor(1, "Dra. Ana Torres", "Cardiología", 4.9, "12 años exp.", 128, "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."),
    Doctor(2, "Dr. Luis Vega", "Pediatría", 4.7, "8 años exp.", 95, "Especialista en pediatría general y desarrollo infantil."),
    Doctor(3, "Dra. Rosa Díaz", "Dermatología", 4.8, "10 años exp.", 110, "Especialista en dermatología clínica y estética.")
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

// ==========================================
// MAIN APP COMPOSABLE & NAVIGATION DRAWER
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicaApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val appointmentsList = remember {
        mutableStateListOf(
            Appointment("Dra. Ana Torres", "Cardiología", "Viernes 27", "10:30 am", "Confirmada"),
            Appointment("Dr. Luis Vega", "Pediatría", "Miércoles 15", "3:00 pm", "Completada")
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                // Drawer Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MG",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Mayra García",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Paciente",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                // Drawer Items (highlighting selected route matching reference image)
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == "inicio",
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("inicio") {
                            popUpTo("inicio") { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Mis citas") },
                    selected = currentRoute == "mis_citas",
                    icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("mis_citas")
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Historial médico") },
                    selected = currentRoute == "historial",
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("historial")
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = currentRoute == "perfil_usuario",
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil_usuario")
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") {
                InicioScreen(
                    doctors = sampleDoctors,
                    onDoctorClick = { doctor ->
                        navController.navigate("perfil/${doctor.id}")
                    },
                    onMenuClick = {
                        scope.launch { drawerState.open() }
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
                    onBackClick = { navController.popBackStack() },
                    onConfirmarCita = { date, time ->
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
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val time = backStackEntry.arguments?.getString("time") ?: ""

                ConfirmacionScreen(
                    doctorName = doctorName,
                    dateTimeText = "$date, $time",
                    onVerMisCitas = {
                        navController.navigate("mis_citas") {
                            popUpTo("inicio")
                        }
                    }
                )
            }

            composable("mis_citas") {
                MisCitasScreen(
                    appointments = appointmentsList,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("historial") {
                HistorialMedicoScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("perfil_usuario") {
                PerfilUsuarioScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

// ==========================================
// 1. INICIO SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    doctors: List<Doctor>,
    onDoctorClick: (Doctor) -> Unit,
    onMenuClick: () -> Unit
) {
    val specialties = listOf("Cardiología", "Pediatría", "Dermatología")
    var selectedSpecialty by remember { mutableStateOf("Cardiología") }

    val filteredDoctors = doctors.filter { it.specialty == selectedSpecialty }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clínica Salud+") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                text = "Hola, Mayra",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Specialty Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(specialties) { specialty ->
                    val isSelected = selectedSpecialty == specialty
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedSpecialty = specialty },
                        label = { Text(specialty) }
                    )
                }
            }

            Text(
                text = "Médicos disponibles",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Doctors List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
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
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Purple avatar circle with plus icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = doctor.specialty,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "⭐", fontSize = 12.sp)
                Text(
                    text = "${doctor.rating}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================
// 2. PERFIL DEL MÉDICO SCREEN
// ==========================================

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
                title = { Text("Perfil del médico") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Avatar Circle with Plus
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${doctor.specialty} · ${doctor.experience}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "⭐", fontSize = 14.sp)
                    Text(
                        text = "${doctor.rating} (${doctor.reviewsCount} reseñas)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = doctor.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = onAgendarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Agendar cita", fontSize = 16.sp)
            }
        }
    }
}

// ==========================================
// 3. AGENDAR CITA SCREEN
// ==========================================

data class DateOption(val dayName: String, val dayNumber: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    onBackClick: () -> Unit,
    onConfirmarCita: (String, String) -> Unit
) {
    val dates = listOf(
        DateOption("Jue", "26"),
        DateOption("Vie", "27"),
        DateOption("Sáb", "28")
    )
    val times = listOf("9:00", "10:30", "3:00")

    var selectedDate by remember { mutableStateOf("Vie 27") }
    var selectedTime by remember { mutableStateOf("10:30") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Date Section matching Figure 1 design (Day top, Number bottom)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Selecciona fecha",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        dates.forEach { dateOpt ->
                            val dateString = "${dateOpt.dayName} ${dateOpt.dayNumber}"
                            val isSelected = selectedDate == dateString
                            DateSelectionChip(
                                dayName = dateOpt.dayName,
                                dayNumber = dateOpt.dayNumber,
                                isSelected = isSelected,
                                onClick = { selectedDate = dateString }
                            )
                        }
                    }
                }

                // Time Section matching Figure 1 design
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Selecciona hora",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        times.forEach { time ->
                            val isSelected = selectedTime == time
                            TimeSelectionChip(
                                time = time,
                                isSelected = isSelected,
                                onClick = { selectedTime = time }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { onConfirmarCita(selectedDate, selectedTime) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Confirmar cita", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DateSelectionChip(
    dayName: String,
    dayNumber: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.size(width = 72.dp, height = 72.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = dayName,
                color = contentColor,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = dayNumber,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun TimeSelectionChip(
    time: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.size(width = 90.dp, height = 50.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = time,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// ==========================================
// 4. CONFIRMACIÓN SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(
    doctorName: String,
    dateTimeText: String,
    onVerMisCitas: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Green checkmark badge
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = "¡Cita agendada!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = doctorName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = dateTimeText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = onVerMisCitas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = "Ver mis citas", fontSize = 16.sp)
            }
        }
    }
}

// ==========================================
// 5. MIS CITAS SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(
    appointments: List<Appointment>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment = appointment)
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    val isConfirmed = appointment.status == "Confirmada"
    val badgeContainerColor = if (isConfirmed) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant
    val badgeContentColor = if (isConfirmed) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant
    val accentColor = if (isConfirmed) Color(0xFF4CAF50) else Color.Transparent

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left vertical accent bar matching Figure 2 design
            if (isConfirmed) {
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(80.dp)
                        .background(accentColor)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = appointment.doctorName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${appointment.date}, ${appointment.time}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = badgeContainerColor
                ) {
                    Text(
                        text = appointment.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = badgeContentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ==========================================
// 6. HISTORIAL MÉDICO SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMedicoScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial médico") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
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
            // Patient Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column {
                            Text(
                                text = "Mayra García",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Paciente · ID: 849203",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Tipo de Sangre",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "🩸 O+",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                text = "Alergias",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "⚠️ Ninguna",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Past Consultations Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Consultas Anteriores",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    ConsultationItem(
                        date = "24/09/2026",
                        doctor = "Dra. Ana Torres",
                        specialty = "Cardiología",
                        status = "Completada"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    ConsultationItem(
                        date = "10/08/2026",
                        doctor = "Dr. Luis Vega",
                        specialty = "Pediatría",
                        status = "Completada"
                    )
                }
            }
        }
    }
}

@Composable
fun ConsultationItem(
    date: String,
    doctor: String,
    specialty: String,
    status: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = doctor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$specialty · $date",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = status,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================
// 7. PERFIL DE USUARIO SCREEN
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MG",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Mayra García",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Paciente registrado",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileInfoRow(label = "Correo electrónico", value = "mayra.garcia@clinicasalud.com")
                    HorizontalDivider()
                    ProfileInfoRow(label = "Teléfono", value = "+52 555 123 4567")
                    HorizontalDivider()
                    ProfileInfoRow(label = "Tipo de Sangre", value = "O+")
                    HorizontalDivider()
                    ProfileInfoRow(label = "ID de Paciente", value = "#849203")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
