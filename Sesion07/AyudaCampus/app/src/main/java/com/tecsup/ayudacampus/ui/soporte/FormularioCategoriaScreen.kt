package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria

private val iconos = listOf("pc" to "▣", "code" to "‹›", "wifi" to "⌁", "key" to "⚿", "printer" to "▤", "tag" to "◇")

@Composable
fun FormularioCategoriaScreen(categoria: Categoria? = null, onGuardar: (Categoria) -> Unit, onCancelar: () -> Unit) {
    var nombre by remember(categoria?.id) { mutableStateOf(categoria?.nombre.orEmpty()) }
    var icono by remember(categoria?.id) { mutableStateOf(categoria?.icono ?: "pc") }
    var activa by remember(categoria?.id) { mutableStateOf(categoria?.activa ?: true) }
    var error by remember { mutableStateOf<String?>(null) }
    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(if (categoria == null) "Nueva categoría" else "Editar categoría", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(nombre, { nombre = it; error = null }, Modifier.fillMaxWidth(), label = { Text("Nombre de la categoría") }, singleLine = true, isError = error != null)
            Text("Icono", style = MaterialTheme.typography.titleSmall)
            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(148.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(iconos) { (clave, dibujo) ->
                    val seleccionado = icono == clave
                    OutlinedCard(onClick = { icono = clave }, colors = CardDefaults.outlinedCardColors(containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)) {
                        Box(Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center) { Text("$dibujo  $clave") }
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text("Categoría activa"); Text("Las categorías inactivas no se ofrecen al crear tickets", style = MaterialTheme.typography.bodySmall) }
                Switch(checked = activa, onCheckedChange = { activa = it })
            }
            Text("Vista previa", style = MaterialTheme.typography.titleSmall)
            Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(iconos.first { it.first == icono }.second, style = MaterialTheme.typography.titleLarge)
                Column { Text(nombre.ifBlank { "Nombre de categoría" }, style = MaterialTheme.typography.titleMedium); Text(if (activa) "Activa" else "Inactiva", style = MaterialTheme.typography.bodySmall) }
            } }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(onClick = { if (nombre.isBlank()) error = "El nombre es obligatorio." else onGuardar(Categoria(id = categoria?.id ?: 0, nombre = nombre.trim(), icono = icono, activa = activa)) }, modifier = Modifier.fillMaxWidth()) { Text("Guardar categoría") }
            OutlinedButton(onClick = onCancelar, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
        }
    }
}
