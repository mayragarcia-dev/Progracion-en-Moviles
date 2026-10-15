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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.domain.model.Prioridad
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

private val CianEstudiante = Color(0xFF0891B2)

@Composable
fun NuevoTicketScreen(
    vm: NuevoTicketViewModel,
    onTicketCreado: () -> Unit,
    onTicketsClick: () -> Unit
) {
    val state by vm.uiState.collectAsState()
    val categorias by vm.categorias.collectAsState()

    LaunchedEffect(state.ticketCreadoId) {
        if (state.ticketCreadoId != null) onTicketCreado()
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onTicketsClick,
                    icon = { Text("▤") },
                    label = { Text("Tickets") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("＋", color = CianEstudiante) },
                    label = { Text("Nuevo", color = CianEstudiante) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Text("♙") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("‹", modifier = Modifier.clickable(onClick = onTicketsClick), style = MaterialTheme.typography.headlineMedium, color = CianEstudiante)
                    Text("Nuevo ticket", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }

                Text("Categoría", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categorias.forEach { categoria ->
                        FilterChip(
                            selected = state.categoriaSeleccionada?.id == categoria.id,
                            onClick = { vm.seleccionarCategoria(categoria) },
                            label = { Text("${iconoCategoria(categoria)} ${categoria.nombre}") }
                        )
                    }
                }
                state.errorCategoria?.let { ErrorCampo(it) }

                OutlinedTextField(
                    value = state.titulo,
                    onValueChange = vm::cambiarTitulo,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Título") },
                    placeholder = { Text("Resume el problema") },
                    singleLine = true,
                    isError = state.errorTitulo != null,
                    supportingText = { state.errorTitulo?.let { mensaje -> Text(mensaje) } }
                )
                OutlinedTextField(
                    value = state.ubicacion,
                    onValueChange = vm::cambiarUbicacion,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Ubicación") },
                    placeholder = { Text("Ej. Laboratorio 3 · Pabellón B") },
                    singleLine = true
                )

                Text("Prioridad", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Prioridad.entries.forEach { prioridad ->
                        FilterChip(
                            selected = state.prioridad == prioridad,
                            onClick = { vm.seleccionarPrioridad(prioridad) },
                            label = {
                                Text(
                                    when (prioridad) {
                                        Prioridad.BAJA -> "Baja"
                                        Prioridad.MEDIA -> "Media"
                                        Prioridad.ALTA -> "Alta"
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                OutlinedTextField(
                    value = state.descripcion,
                    onValueChange = vm::cambiarDescripcion,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Descripción") },
                    placeholder = { Text("Cuéntanos qué ocurrió y desde cuándo") },
                    minLines = 4,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isError = state.errorDescripcion != null,
                    supportingText = { state.errorDescripcion?.let { mensaje -> Text(mensaje) } }
                )

                Text(
                    "◷  Tiempo de respuesta estimado: 4 horas",
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    color = CianEstudiante,
                    style = MaterialTheme.typography.bodySmall
                )
                state.errorEnvio?.let { ErrorCampo(it) }
                Button(
                    onClick = vm::enviar,
                    enabled = !state.enviando,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CianEstudiante),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Text(if (state.enviando) "Enviando…" else "Enviar ticket", fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun ErrorCampo(mensaje: String) {
    Text(mensaje, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
}

private fun iconoCategoria(categoria: Categoria): String = when (categoria.icono.lowercase()) {
    "wifi" -> "◉"
    "code" -> "‹›"
    "key" -> "⌁"
    "printer" -> "▤"
    "pc" -> "▣"
    else -> "◇"
}
