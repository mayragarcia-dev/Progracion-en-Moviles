package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.Prioridad
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel

@Composable
fun AtenderTicketScreen(vm: AtenderViewModel, onVolver: () -> Unit) {
    val item by vm.ticket.collectAsState()
    val estadoSeleccionado by vm.estado.collectAsState()
    val prioridadSeleccionada by vm.prioridad.collectAsState()
    var respuesta by remember { mutableStateOf("") }
    val mensaje by vm.mensaje.collectAsState()
    Scaffold { padding ->
        val actual = item
        if (actual == null) Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) { Text("Cargando ticket…") }
        else {
            val cerrado = actual.ticket.estado == EstadoTicket.CERRADO.name
            LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), contentPadding = PaddingValues(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("‹  Atender ticket #${actual.ticket.id.toString().padStart(4, '0')}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth()) }
                item {
                    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(actual.ticket.titulo, style = MaterialTheme.typography.titleMedium)
                        Text("${actual.categoria.nombre} · ${actual.ticket.ubicacion}")
                        Text(actual.ticket.descripcion)
                        HorizontalDivider()
                        Text("Cliente: ${actual.cliente.nombre} · ${actual.cliente.correo}", style = MaterialTheme.typography.bodySmall)
                    } }
                }
                item { Text("Estado", style = MaterialTheme.typography.titleSmall) }
                item { Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO, EstadoTicket.CERRADO).forEach { e ->
                        FilterChip(selected = (estadoSeleccionado?.name ?: actual.ticket.estado) == e.name, enabled = !cerrado && e != EstadoTicket.CERRADO,
                            onClick = { vm.cambiarEstado(e) }, label = { Text(when(e) { EstadoTicket.ABIERTO -> "Abierto"; EstadoTicket.EN_PROCESO -> "En proceso"; EstadoTicket.RESUELTO -> "Resuelto"; EstadoTicket.CERRADO -> "Cerrado" }) })
                    }
                } }
                item { Text("Prioridad", style = MaterialTheme.typography.titleSmall) }
                item { Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) { Prioridad.entries.forEach { p ->
                    FilterChip(selected = (prioridadSeleccionada?.name ?: actual.ticket.prioridad) == p.name, enabled = !cerrado, onClick = { vm.cambiarPrioridad(p) }, label = { Text(p.name.lowercase().replaceFirstChar(Char::uppercase)) })
                } } }
                item { Text("Conversación", style = MaterialTheme.typography.titleSmall) }
                items(actual.comentarios.sortedBy { it.fecha }, key = { it.id }) { BurbujaComentario(it, it.autorId == vm.usuarioActual.id) }
                item {
                    OutlinedTextField(respuesta, { respuesta = it }, Modifier.fillMaxWidth(), label = { Text("Responder al cliente…") }, enabled = !cerrado, minLines = 2)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.responder(respuesta); respuesta = "" }, enabled = !cerrado && respuesta.isNotBlank(), modifier = Modifier.fillMaxWidth()) { Text("Enviar respuesta") }
                    Spacer(Modifier.height(4.dp))
                    Button(onClick = vm::guardar, enabled = !cerrado, modifier = Modifier.fillMaxWidth()) { Text("Guardar cambios") }
                    mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    if (cerrado) Text("El ticket está cerrado y no se puede modificar.", color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) { Text("Volver a tickets") }
                }
            }
        }
    }
}
