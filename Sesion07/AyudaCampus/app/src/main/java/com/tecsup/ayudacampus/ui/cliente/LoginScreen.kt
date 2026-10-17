package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    onLoginSuccess: (Rol) -> Unit
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
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(12.dp))
        AyudaTextField(
            value = clave,
            onValueChange = { clave = it; vm.limpiarError() },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            esContrasena = true
        )
        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error.orEmpty(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { vm.login(correo, clave, rolSeleccionado) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorRol)
        ) { Text("Ingresar", fontWeight = FontWeight.SemiBold) }
    }
}
