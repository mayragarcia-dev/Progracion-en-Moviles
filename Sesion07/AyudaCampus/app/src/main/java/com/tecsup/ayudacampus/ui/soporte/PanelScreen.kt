package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.graficos.GraficoBarras
import com.tecsup.ayudacampus.ui.graficos.GraficoDona
import com.tecsup.ayudacampus.viewmodel.PanelViewModel

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
            Text("Panel de soporte", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Mesa de ayuda TI · Hoy", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
        Text(etiqueta, color = color, style = MaterialTheme.typography.labelSmall)
    }
}
