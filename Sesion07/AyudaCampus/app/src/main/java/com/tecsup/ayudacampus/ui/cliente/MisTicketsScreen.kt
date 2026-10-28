package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.FiltroTickets
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel

@Composable
fun MisTicketsScreen(
    vm: MisTicketsViewModel,
    onTicketClick: (Int) -> Unit,
    onNuevo: () -> Unit,
    onPerfil: () -> Unit = {}
) {
    val tickets by vm.tickets.collectAsState()
    val filtro by vm.filtroSeleccionado.collectAsState()
    val textoBusqueda by vm.textoBusqueda.collectAsState()
    val abiertos by vm.cantidadAbiertos.collectAsState()
    val categorias by vm.categorias.collectAsState()
    val categoriaFiltro by vm.categoriaFiltroNombre.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevo, containerColor = ColorEstudiante) {
                Text("+", color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.headlineMedium)
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("▤") },
                    label = { Text("Tickets") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNuevo,
                    icon = { Text("＋") },
                    label = { Text("Nuevo") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onPerfil,
                    icon = { Text("♙") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            Text("Mis tickets", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(
                "Hola, ${vm.usuario.nombre.substringBefore(' ')} · $abiertos abiertos",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = vm::buscar,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                placeholder = { Text("Buscar por título o número...") },
                leadingIcon = { Text("⌕", modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.titleLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FiltroTickets.entries.forEach { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick = { vm.seleccionarFiltro(opcion) },
                        label = { Text(opcion.etiqueta) }
                    )
                }
            }
            if (categorias.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    categorias.forEach { cat ->
                        FilterChip(
                            selected = categoriaFiltro == cat.nombre,
                            onClick = { vm.seleccionarCategoriaFiltro(cat.nombre) },
                            label = { Text(cat.nombre, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            if (tickets.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No tienes tickets aquí", style = MaterialTheme.typography.titleMedium)
                    Text("Prueba otro filtro o crea un ticket nuevo.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tickets, key = { it.id }) { ticket ->
                        TicketCard(ticket = ticket, onClick = { onTicketClick(ticket.id) })
                    }
                }
            }
        }
    }
}

private val ColorEstudiante = androidx.compose.ui.graphics.Color(0xFF0891B2)
