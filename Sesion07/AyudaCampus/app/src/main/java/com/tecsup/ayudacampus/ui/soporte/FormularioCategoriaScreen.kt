package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria

private val iconos = listOf(
    "pc" to "💻",
    "code" to "⟨⟩",
    "wifi" to "📶",
    "key" to "🔑",
    "printer" to "🖨️",
    "tag" to "🏷️"
)

@Composable
fun FormularioCategoriaScreen(categoria: Categoria? = null, onGuardar: (Categoria) -> Unit, onCancelar: () -> Unit) {
    var nombre by remember(categoria?.id) { mutableStateOf(categoria?.nombre.orEmpty()) }
    var icono by remember(categoria?.id) { mutableStateOf(categoria?.icono ?: "pc") }
    var activa by remember(categoria?.id) { mutableStateOf(categoria?.activa ?: true) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancelar) {
                    Text("‹", style = MaterialTheme.typography.headlineMedium)
                }
                Spacer(Modifier.width(4.dp))
                Text(
                    if (categoria == null) "Nueva categoría" else "Editar categoría",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; error = null },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre de la categoría") },
                singleLine = true,
                isError = error != null,
                shape = RoundedCornerShape(14.dp)
            )

            Text("Ícono", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(160.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(iconos) { (clave, dibujo) ->
                    val seleccionado = icono == clave
                    OutlinedCard(
                        onClick = { icono = clave },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        ),
                        border = if (seleccionado) CardDefaults.outlinedCardBorder().copy(width = 2.dp) else CardDefaults.outlinedCardBorder()
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(68.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(dibujo, style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Categoría activa", fontWeight = FontWeight.Medium)
                        Text("Los estudiantes podrán elegirla", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = activa, onCheckedChange = { activa = it })
                }
            }

            Text("Vista previa", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                iconos.firstOrNull { it.first == icono }?.second ?: "💻",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    Column {
                        Text(
                            nombre.ifBlank { "Nombre de categoría" },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("0 tickets", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            if (error != null) {
                Text(error.orEmpty(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    if (nombre.isBlank()) {
                        error = "El nombre es obligatorio."
                    } else {
                        onGuardar(Categoria(id = categoria?.id ?: 0, nombre = nombre.trim(), icono = icono, activa = activa))
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
            ) {
                Text("Guardar categoría", fontWeight = FontWeight.SemiBold)
            }

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Cancelar", fontWeight = FontWeight.SemiBold, color = Color(0xFF7C3AED))
            }
        }
    }
}
