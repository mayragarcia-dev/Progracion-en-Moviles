package com.tecsup.ayudacampus.ui.cliente

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.RolesUsuario
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.ui.components.EstadoChip
import com.tecsup.ayudacampus.ui.components.PrioridadChip
import com.tecsup.ayudacampus.ui.components.StepperEstrellas
import com.tecsup.ayudacampus.ui.components.StepperTicket
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel

private val CianDetalle = Color(0xFF0891B2)

@Composable
fun DetalleTicketScreen(
    vm: TicketDetalleViewModel,
    onTicketsClick: () -> Unit,
    onNuevoClick: () -> Unit
) {
    val detalle by vm.ticket.collectAsState()
    val calificacion by vm.calificacion.collectAsState()
    val error by vm.error.collectAsState()
    var texto by remember { mutableStateOf("") }
    var comentarioFinal by remember { mutableStateOf("") }
    val esSoporte = vm.usuarioActual.rol == RolesUsuario.ADMIN

    LaunchedEffect(detalle?.ticket?.estado) {
        if (detalle?.ticket?.estado != EstadoTicket.RESUELTO.name) {
            comentarioFinal = ""
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onTicketsClick, icon = { Text("▤") }, label = { Text("Tickets") })
                NavigationBarItem(false, onNuevoClick, icon = { Text("＋") }, label = { Text("Nuevo") })
                NavigationBarItem(false, {}, icon = { Text("♙") }, label = { Text("Perfil") })
            }
        }
    ) { padding ->
        if (detalle == null) {
            Column(Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("Cargando ticket…")
            }
        } else {
            val item = requireNotNull(detalle)
            val ticket = item.ticket
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("‹", modifier = Modifier.clickable(onClick = onTicketsClick).padding(end = 12.dp), color = CianDetalle, style = MaterialTheme.typography.headlineMedium)
                        Text("Ticket #${ticket.id.toString().padStart(4, '0')}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    }
                }
                item {
                    Surface(shape = RoundedCornerShape(16.dp), tonalElevation = 1.dp, color = MaterialTheme.colorScheme.surface) {
                        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(ticket.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                EstadoChip(ticket.estado)
                                PrioridadChip(ticket.prioridad)
                            }
                            Text("◇  ${item.categoria.nombre}    ·    ${ticket.ubicacion}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                item { StepperTicket(ticket.estado) }

                if (esSoporte && ticket.estado != EstadoTicket.CERRADO.name) {
                    item {
                        Text("Cambiar estado", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO).forEach { estado ->
                                FilterChip(
                                    selected = ticket.estado == estado.name,
                                    onClick = { vm.cambiarEstado(estado) },
                                    label = { Text(when (estado) {
                                        EstadoTicket.ABIERTO -> "Abierto"
                                        EstadoTicket.EN_PROCESO -> "En proceso"
                                        else -> "Resuelto"
                                    }) }
                                )
                            }
                        }
                    }
                }

                item {
                    Text("Conversación", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                }
                items(item.comentarios.sortedBy { it.fecha }, key = { it.id }) { comentario ->
                    BurbujaComentario(comentario, propio = comentario.autorId == vm.usuarioActual.id)
                }

                when (ticket.estado) {
                    EstadoTicket.RESUELTO.name -> {
                        if (!esSoporte) {
                            item {
                                Surface(color = Color(0xFFDCFCE7), shape = RoundedCornerShape(14.dp)) {
                                    Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("✓  Soporte marcó este ticket como resuelto.", color = Color(0xFF166534), fontWeight = FontWeight.SemiBold)
                                        Text("¿El problema se solucionó?", color = Color(0xFF166534), style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                            item {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Text("Califica la atención", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                    StepperEstrellas(calificacion, vm::seleccionarCalificacion)
                                    Text(if (calificacion == 0) "Selecciona de 1 a 5 estrellas" else "$calificacion de 5 estrellas", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            item {
                                OutlinedTextField(
                                    value = comentarioFinal,
                                    onValueChange = { comentarioFinal = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Comentario final (opcional)") },
                                    minLines = 2,
                                    maxLines = 3
                                )
                            }
                            item {
                                Button(
                                    onClick = { vm.cerrar(calificacion, comentarioFinal) },
                                    enabled = calificacion in 1..5,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = CianDetalle)
                                ) { Text("Confirmar y cerrar") }
                                OutlinedButton(onClick = vm::reabrir, modifier = Modifier.fillMaxWidth()) { Text("Reabrir ticket") }
                                error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
                            }
                        } else {
                            item {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = texto,
                                        onValueChange = { texto = it },
                                        modifier = Modifier.weight(1f),
                                        placeholder = { Text("Escribe una respuesta…") },
                                        shape = RoundedCornerShape(24.dp),
                                        singleLine = true
                                    )
                                    Button(
                                        onClick = { vm.comentar(texto); texto = "" },
                                        enabled = texto.isNotBlank(),
                                        shape = RoundedCornerShape(50.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = CianDetalle)
                                    ) { Text("➤") }
                                }
                                error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
                            }
                        }
                    }
                    EstadoTicket.CERRADO.name -> {
                        item {
                            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Atención calificada", fontWeight = FontWeight.SemiBold)
                                StepperEstrellas(ticket.calificacion ?: 0, onSeleccionar = {}, habilitado = false)
                                Text("${ticket.calificacion ?: 0} de 5 estrellas", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    else -> {
                        if (ticket.estado != EstadoTicket.CERRADO.name) {
                            item {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = texto,
                                        onValueChange = { texto = it },
                                        modifier = Modifier.weight(1f),
                                        placeholder = { Text("Escribe un comentario…") },
                                        shape = RoundedCornerShape(24.dp),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                    )
                                    Button(
                                        onClick = { vm.comentar(texto); texto = "" },
                                        enabled = texto.isNotBlank(),
                                        shape = RoundedCornerShape(50.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = CianDetalle)
                                    ) { Text("➤") }
                                }
                                error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
                            }
                        }
                    }
                }
            }
        }
    }
}
