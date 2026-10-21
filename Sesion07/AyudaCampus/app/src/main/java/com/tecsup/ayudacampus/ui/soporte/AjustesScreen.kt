package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onPanel, icon = { Text("▥") }, label = { Text("Panel") })
                NavigationBarItem(false, onTickets, icon = { Text("▤") }, label = { Text("Tickets") })
                NavigationBarItem(false, onCategorias, icon = { Text("◇") }, label = { Text("Categorías") })
                NavigationBarItem(true, {}, icon = { Text("⚙") }, label = { Text("Ajustes") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ajustes", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }

            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("👤", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(usuario.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(usuario.correo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                "Perfil activo",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Cambiar de perfil button
            Button(
                onClick = onCambiarPerfil,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("⇄", style = MaterialTheme.typography.headlineSmall)
                        Column {
                            Text("Cambiar de perfil", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                            Text("Modo práctica: ir a vista Estudiante", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                    Text("›", style = MaterialTheme.typography.titleLarge)
                }
            }

            Text("Preferencias", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AjusteSwitch("Modo oscuro", "Cambia el tema de la app", oscuro) { scope.launch { preferencias.guardarModoOscuro(it) } }
                AjusteSwitch("Notificaciones", "Avisar tickets nuevos", notificaciones) { scope.launch { preferencias.guardarNotificaciones(it) } }
                AjusteSwitch("Recibir tickets", "Abrir o cerrar la mesa de ayuda", recibirTickets) { scope.launch { preferencias.guardarRecibirTickets(it) } }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Se guardan en el dispositivo", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("con DataStore", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }

            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFDC2626)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626))
            ) {
                Text("Cerrar sesión", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun AjusteSwitch(titulo: String, detalle: String, activo: Boolean, onChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(
                checked = activo,
                onCheckedChange = onChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF7C3AED))
            )
        }
    }
}
