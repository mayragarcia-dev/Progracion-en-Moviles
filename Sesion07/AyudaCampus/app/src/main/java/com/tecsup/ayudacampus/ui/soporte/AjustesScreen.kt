package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import kotlinx.coroutines.launch

@Composable
fun AjustesScreen(
    usuario: Usuario,
    preferencias: PreferenciasStore,
    onPanel: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val oscuro by preferencias.modoOscuro.collectAsState(initial = false)
    val notificaciones by preferencias.notificaciones.collectAsState(initial = true)
    val recibirTickets by preferencias.recibirTickets.collectAsState(initial = true)
    val scope = rememberCoroutineScope()
    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(false, onPanel, icon = { Text("▥") }, label = { Text("Panel") })
            NavigationBarItem(false, onTickets, icon = { Text("▤") }, label = { Text("Tickets") })
            NavigationBarItem(false, onCategorias, icon = { Text("◇") }, label = { Text("Categorías") })
            NavigationBarItem(true, {}, icon = { Text("⚙") }, label = { Text("Ajustes") })
        }
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Ajustes", style = MaterialTheme.typography.headlineSmall)
            Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("♙", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineLarge)
                Column { Text(usuario.nombre, style = MaterialTheme.typography.titleMedium); Text(usuario.correo, style = MaterialTheme.typography.bodySmall); Text("Perfil activo", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall) }
            } }
            Button(onClick = onCambiarPerfil, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) { Text("⇄   Cambiar de perfil") }
            Text("Modo práctica: cambia a la vista Estudiante sin volver a iniciar sesión.", style = MaterialTheme.typography.bodySmall)
            Text("Preferencias", style = MaterialTheme.typography.titleSmall)
            AjusteSwitch("Modo oscuro", "Cambia el tema de la app", oscuro) { scope.launch { preferencias.guardarModoOscuro(it) } }
            AjusteSwitch("Notificaciones", "Avisar tickets nuevos", notificaciones) { scope.launch { preferencias.guardarNotificaciones(it) } }
            AjusteSwitch("Recibir tickets", "Abrir o cerrar la mesa de ayuda", recibirTickets) { scope.launch { preferencias.guardarRecibirTickets(it) } }
            Text("Se guardan en el dispositivo con DataStore.", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) { Text("Cerrar sesión") }
        }
    }
}

@Composable
private fun AjusteSwitch(titulo: String, detalle: String, activo: Boolean, onChange: (Boolean) -> Unit) {
    Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) { Text(titulo, style = MaterialTheme.typography.titleSmall); Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        Switch(checked = activo, onCheckedChange = onChange)
    } }
}
