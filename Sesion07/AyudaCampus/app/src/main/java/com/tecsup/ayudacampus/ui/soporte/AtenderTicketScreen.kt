package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onVolver) {
                    Text("‹", style = MaterialTheme.typography.headlineMedium)
                }
                Spacer(Modifier.width(4.dp))
                val ticketNum = item?.ticket?.id?.toString()?.padStart(4, '0') ?: ""
                Text(
                    "Atender ticket #$ticketNum",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        val actual = item
        if (actual == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Cargando ticket…")
            }
        } else {
            val cerrado = actual.ticket.estado == EstadoTicket.CERRADO.name
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(actual.ticket.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("${actual.categoria.nombre} · ${actual.ticket.ubicacion}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            Text(actual.ticket.descripcion, style = MaterialTheme.typography.bodyMedium)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                            Text("Cliente: ${actual.cliente.nombre} · ${actual.cliente.correo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                item { Text("Estado", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold) }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO, EstadoTicket.CERRADO).forEach { e ->
                            FilterChip(
                                selected = (estadoSeleccionado?.name ?: actual.ticket.estado) == e.name,
                                enabled = !cerrado && e != EstadoTicket.CERRADO,
                                onClick = { vm.cambiarEstado(e) },
                                label = { Text(when(e) { EstadoTicket.ABIERTO -> "Abierto"; EstadoTicket.EN_PROCESO -> "En proceso"; EstadoTicket.RESUELTO -> "Resuelto"; EstadoTicket.CERRADO -> "Cerrado" }) }
                            )
                        }
                    }
                }

                item { Text("Prioridad", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold) }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Prioridad.entries.forEach { p ->
                            FilterChip(
                                selected = (prioridadSeleccionada?.name ?: actual.ticket.prioridad) == p.name,
                                enabled = !cerrado,
                                onClick = { vm.cambiarPrioridad(p) },
                                label = { Text(p.name.lowercase().replaceFirstChar(Char::uppercase)) }
                            )
                        }
                    }
                }

                item { Text("Conversación", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold) }
                items(actual.comentarios.sortedBy { it.fecha }, key = { it.id }) { comentario ->
                    BurbujaComentario(comentario, comentario.autorId == vm.usuarioActual.id)
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = respuesta,
                            onValueChange = { respuesta = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Responder al cliente…") },
                            enabled = !cerrado,
                            minLines = 2,
                            shape = RoundedCornerShape(14.dp)
                        )
                        Button(
                            onClick = { vm.responder(respuesta); respuesta = "" },
                            enabled = !cerrado && respuesta.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                        ) {
                            Text("Enviar respuesta", fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = vm::guardar,
                            enabled = !cerrado,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                        ) {
                            Text("Guardar cambios", fontWeight = FontWeight.SemiBold)
                        }
                        mensaje?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
                        if (cerrado) {
                            Text("El ticket está cerrado y no se puede modificar.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                        }
                        OutlinedButton(
                            onClick = onVolver,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Volver a tickets", fontWeight = FontWeight.SemiBold, color = Color(0xFF7C3AED))
                        }
                    }
                }
            }
        }
    }
}
