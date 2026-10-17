package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    usuario: Usuario,
    tickets: StateFlow<List<TicketResumen>>,
    preferencias: PreferenciasStore,
    onTickets: () -> Unit,
    onNuevo: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit,
    onIrTickets: () -> Unit = onTickets
) {
    val lista by tickets.collectAsState()
    val modoOscuro by preferencias.modoOscuro.collectAsState(initial = false)
    val notificaciones by preferencias.notificaciones.collectAsState(initial = true)
    val scope = rememberCoroutineScope()
    val abiertos = lista.count { it.estado == EstadoTicket.ABIERTO.name || it.estado == EstadoTicket.EN_PROCESO.name }
    val resueltos = lista.count { it.estado == EstadoTicket.RESUELTO.name || it.estado == EstadoTicket.CERRADO.name }
    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(false, onIrTickets, icon = { Text("▤") }, label = { Text("Tickets") })
            NavigationBarItem(false, onNuevo, icon = { Text("＋") }, label = { Text("Nuevo") })
            NavigationBarItem(true, {}, icon = { Text("♙") }, label = { Text("Perfil") })
        }
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Perfil", style = MaterialTheme.typography.headlineSmall)
            Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("◉", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineLarge)
                Column { Text(usuario.nombre, style = MaterialTheme.typography.titleMedium); Text(usuario.correo, style = MaterialTheme.typography.bodySmall); Text("Estudiante", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall) }
            } }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PerfilContador("$abiertos", "Abiertos", Modifier.weight(1f))
                PerfilContador("$resueltos", "Resueltos", Modifier.weight(1f))
                PerfilContador("${lista.size}", "Total", Modifier.weight(1f))
            }
            Button(onClick = onCambiarPerfil, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) { Text("⇄   Cambiar de perfil") }
            Text("Modo práctica: cambia a Soporte TI sin volver a iniciar sesión.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Preferencias", style = MaterialTheme.typography.titleSmall)
            Preferencia("Modo oscuro", "Cambia la apariencia de la app", modoOscuro) { scope.launch { preferencias.guardarModoOscuro(it) } }
            Preferencia("Notificaciones", "Avisar cambios de estado", notificaciones) { scope.launch { preferencias.guardarNotificaciones(it) } }
            Text("Se guardan en el dispositivo con DataStore.", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) { Text("Cerrar sesión") }
        }
    }
}

@Composable
private fun PerfilContador(valor: String, titulo: String, modifier: Modifier = Modifier) {
    Card(modifier) { Column(Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(valor, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineSmall)
        Text(titulo, style = MaterialTheme.typography.labelSmall)
    } }
}

@Composable
private fun Preferencia(titulo: String, detalle: String, activa: Boolean, onChange: (Boolean) -> Unit) {
    Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) { Text(titulo, style = MaterialTheme.typography.titleSmall); Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        Switch(checked = activa, onCheckedChange = onChange)
    } }
}
