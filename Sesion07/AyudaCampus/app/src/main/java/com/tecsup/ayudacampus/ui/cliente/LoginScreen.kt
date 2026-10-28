package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

private val CianEstudiante = Color(0xFF0891B2)
private val VioletaSoporte = Color(0xFF7C3AED)

@Composable
fun LoginScreen(
    vm: SesionViewModel,
    onLoginSuccess: (Rol) -> Unit,
    onRegistrarClick: () -> Unit
) {
    var rolSeleccionado by remember { mutableStateOf(Rol.CLIENTE) }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    val usuario by vm.usuarioActual.collectAsState()
    val error by vm.error.collectAsState()
    val colorRol = if (rolSeleccionado == Rol.CLIENTE) CianEstudiante else VioletaSoporte

    LaunchedEffect(usuario) {
        usuario?.let { onLoginSuccess(if (it.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("Ingresa para continuar", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(Rol.CLIENTE to "Estudiante", Rol.ADMIN to "Soporte TI").forEach { (rol, etiqueta) ->
                val seleccionado = rolSeleccionado == rol
                if (seleccionado) {
                    Button(
                        onClick = { rolSeleccionado = rol; vm.limpiarError() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (rol == Rol.CLIENTE) CianEstudiante else VioletaSoporte)
                    ) { Text(etiqueta) }
                } else {
                    OutlinedButton(
                        onClick = { rolSeleccionado = rol; vm.limpiarError() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp)
                    ) { Text(etiqueta) }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        AyudaTextField(
            value = correo,
            onValueChange = { correo = it; vm.limpiarError() },
            label = "Correo",
            keyboardType = KeyboardType.Email,
            leadingIconVector = Icons.Default.Email
        )
        Spacer(Modifier.height(12.dp))
        AyudaTextField(
            value = clave,
            onValueChange = { clave = it; vm.limpiarError() },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            esContrasena = true,
            leadingIconVector = Icons.Default.Lock
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { /* TODO: Olvidaste tu contraseña */ }, contentPadding = PaddingValues(0.dp)) {
                Text("¿Olvidaste tu contraseña?", style = MaterialTheme.typography.bodySmall, color = colorRol)
            }
        }

        if (error != null) {
            Spacer(Modifier.height(4.dp))
            Text(error.orEmpty(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.login(correo, clave, rolSeleccionado) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorRol)
        ) { Text("Ingresar", fontWeight = FontWeight.SemiBold) }

        Spacer(Modifier.height(16.dp))

        if (rolSeleccionado == Rol.CLIENTE) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿No tienes cuenta? ", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                TextButton(onClick = onRegistrarClick, contentPadding = PaddingValues(0.dp)) {
                    Text("Regístrate", fontWeight = FontWeight.Bold, color = CianEstudiante)
                }
            }
        } else {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Modo práctica", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VioletaSoporte)
                    Spacer(Modifier.height(2.dp))
                    Text("Puedes cambiar de perfil desde Ajustes para probar ambos roles.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
