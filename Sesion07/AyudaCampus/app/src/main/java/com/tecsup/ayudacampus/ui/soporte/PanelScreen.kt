package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.graficos.GraficoBarras
import com.tecsup.ayudacampus.ui.graficos.GraficoDona
import com.tecsup.ayudacampus.viewmodel.PanelViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val VioletaPanel = Color(0xFF7C3AED)

@Composable
fun PanelScreen(
    vm: PanelViewModel,
    onVerTickets: () -> Unit,
    onBandejaClick: () -> Unit,
    onAjustes: () -> Unit = {},
    onCategorias: () -> Unit = {}
) {
    val abiertos by vm.abiertos.collectAsState()
    val enProceso by vm.enProceso.collectAsState()
    val resueltos by vm.resueltos.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porCategoria by vm.porCategoria.collectAsState()
    val altas by vm.altasSinAtender.collectAsState()
    val promedioResolucion by vm.promedioResolucion.collectAsState()
    val notificaciones by vm.notificaciones.collectAsState()

    var mostrarNotificaciones by remember { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(true, {}, icon = { Text("▥", color = VioletaPanel) }, label = { Text("Panel", color = VioletaPanel) })
                NavigationBarItem(false, onBandejaClick, icon = { Text("▤") }, label = { Text("Tickets") })
                NavigationBarItem(false, onCategorias, icon = { Text("▦") }, label = { Text("Categorías") })
                NavigationBarItem(false, onAjustes, icon = { Text("⚙") }, label = { Text("Ajustes") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Panel de soporte", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Mesa de ayuda TI · Hoy", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = {
                        mostrarNotificaciones = true
                        vm.marcarNotificacionesLeidas()
                    }) {
                        Text("🔔", style = MaterialTheme.typography.titleLarge)
                    }
                    if (notificaciones.any { !it.leida }) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Red, CircleShape)
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ContadorPanel("$abiertos", "Abiertos", Color(0xFFFFF1CC), Color(0xFFB45309), Modifier.weight(1f))
                ContadorPanel("$enProceso", "En proceso", Color(0xFFDBEAFE), Color(0xFF2563EB), Modifier.weight(1f))
                ContadorPanel("$resueltos", "Resueltos", Color(0xFFDCFCE7), Color(0xFF16A34A), Modifier.weight(1f))
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("◷", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                    Column {
                        Text("Tiempo promedio de resolución", style = MaterialTheme.typography.labelMedium)
                        Text(
                            promedioResolucion ?: "Sin tickets cerrados",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text("Tickets por estado", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            GraficoDona(porEstado)
            Text("Tickets por categoría", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            GraficoBarras(porCategoria)

            if (altas > 0) {
                Surface(color = Color(0xFFFEE2E2), shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("⚠", color = Color(0xFFDC2626), style = MaterialTheme.typography.titleLarge)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("$altas tickets de prioridad Alta", color = Color(0xFF991B1B), fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
                            Text("sin atender hace más de 1 hora", color = Color(0xFF991B1B), style = MaterialTheme.typography.labelSmall)
                        }
                        Button(
                            onClick = onVerTickets,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                        ) { Text("Ver") }
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
        }
    }

    if (mostrarNotificaciones) {
        AlertDialog(
            onDismissRequest = { mostrarNotificaciones = false },
            title = { Text("Notificaciones de Tickets", fontWeight = FontWeight.Bold) },
            text = {
                if (notificaciones.isEmpty()) {
                    Text("No hay notificaciones de tickets nuevos.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(notificaciones, key = { it.id }) { notif ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (notif.leida) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(notif.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                    Text(notif.mensaje, style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        formatter.format(Date(notif.fecha)),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { mostrarNotificaciones = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
private fun ContadorPanel(
    valor: String,
    etiqueta: String,
    fondo: Color,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(fondo, RoundedCornerShape(16.dp)).padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(valor, color = color, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(etiqueta, color = color.copy(alpha = 0.8f), style = MaterialTheme.typography.labelSmall)
    }
}
