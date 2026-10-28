package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

private val CianEstudiante = Color(0xFF0891B2)

@Composable
fun RegistroScreen(
    vm: SesionViewModel,
    onRegistroSuccess: () -> Unit,
    onVolverLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onVolverLogin, contentPadding = PaddingValues(0.dp)) {
            Text("‹ Volver", style = MaterialTheme.typography.titleMedium, color = CianEstudiante)
        }

        Spacer(Modifier.height(8.dp))
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("Regístrate como estudiante para gestionar tus tickets", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))

        AyudaTextField(
            value = nombre,
            onValueChange = { nombre = it; error = null },
            label = "Nombre completo",
            leadingIconVector = Icons.Default.Person
        )
        Spacer(Modifier.height(12.dp))
        AyudaTextField(
            value = correo,
            onValueChange = { correo = it; error = null },
            label = "Correo institucional",
            keyboardType = KeyboardType.Email,
            leadingIconVector = Icons.Default.Email
        )
        Spacer(Modifier.height(12.dp))
        AyudaTextField(
            value = telefono,
            onValueChange = { if (it.length <= 9 && it.all { char -> char.isDigit() }) { telefono = it; error = null } },
            label = "Teléfono (9 dígitos)",
            keyboardType = KeyboardType.Phone,
            leadingIconVector = Icons.Default.Phone
        )
        Spacer(Modifier.height(12.dp))
        AyudaTextField(
            value = clave,
            onValueChange = { clave = it; error = null },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            esContrasena = true,
            leadingIconVector = Icons.Default.Lock
        )

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error.orEmpty(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isBlank() || correo.isBlank() || clave.isBlank() || telefono.isBlank()) {
                    error = "Por favor completa todos los campos."
                } else if (telefono.trim().length != 9 || !telefono.all { it.isDigit() }) {
                    error = "El teléfono debe tener exactamente 9 dígitos."
                } else {
                    vm.registrarEstudiante(
                        nombre = nombre,
                        correo = correo,
                        clave = clave,
                        telefono = telefono,
                        onSuccess = onRegistroSuccess,
                        onError = { err -> error = err }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CianEstudiante)
        ) {
            Text("Registrarse", fontWeight = FontWeight.SemiBold)
        }
    }
}
