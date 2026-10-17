package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FilterChip
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.FiltroBandeja
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    onTicketClick: (Int) -> Unit,
    onPanelClick: () -> Unit = {},
    onCategoriasClick: () -> Unit = {},
    onAjustesClick: () -> Unit = {}
) {
    val tickets by vm.tickets.collectAsState()
    val filtro by vm.filtroSeleccionado.collectAsState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onPanelClick, icon = { Text("▥") }, label = { Text("Panel") })
                NavigationBarItem(true, {}, icon = { Text("▤") }, label = { Text("Tickets") })
                NavigationBarItem(false, onCategoriasClick, icon = { Text("▦") }, label = { Text("Categorías") })
                NavigationBarItem(false, onAjustesClick, icon = { Text("⚙") }, label = { Text("Ajustes") })
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text("Tickets", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("${tickets.size} solicitudes del campus", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FiltroBandeja.entries.forEach { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick = { vm.seleccionarFiltro(opcion) },
                        label = { Text(opcion.etiqueta) }
                    )
                }
            }
            if (tickets.isEmpty()) {
                Column(Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("No hay tickets para atender")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).padding(top = 12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tickets, key = { it.id }) { ticket ->
                        TicketCard(ticket = ticket, onClick = { onTicketClick(ticket.id) }, mostrarCliente = true)
                    }
                }
            }
        }
    }
}
