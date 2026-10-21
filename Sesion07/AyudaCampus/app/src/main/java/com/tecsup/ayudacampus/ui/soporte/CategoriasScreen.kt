package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

private val iconoMap = mapOf(
    "pc" to "💻",
    "code" to "⟨⟩",
    "wifi" to "📶",
    "key" to "🔑",
    "printer" to "🖨️",
    "tag" to "🏷️"
)

@Composable
fun CategoriasScreen(
    vm: CategoriasViewModel,
    onEditar: (Int) -> Unit,
    onNueva: () -> Unit = {},
    onPanelClick: () -> Unit = {},
    onTicketsClick: () -> Unit = {},
    onAjustesClick: () -> Unit = {}
) {
    val categorias by vm.categorias.collectAsState()
    val mensaje by vm.mensaje.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onPanelClick, icon = { Text("▥") }, label = { Text("Panel") })
                NavigationBarItem(false, onTicketsClick, icon = { Text("▤") }, label = { Text("Tickets") })
                NavigationBarItem(true, {}, icon = { Text("◇") }, label = { Text("Categorías") })
                NavigationBarItem(false, onAjustesClick, icon = { Text("⚙") }, label = { Text("Ajustes") })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Categorías de soporte",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onNueva,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
            ) {
                Text("+ Nueva categoría", fontWeight = FontWeight.SemiBold)
            }

            mensaje?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categorias, key = { it.id }) { categoria ->
                    CategoriaItem(
                        categoria = categoria,
                        onToggle = { vm.alternar(categoria) },
                        onEditar = { onEditar(categoria.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoriaItem(categoria: Categoria, onToggle: () -> Unit, onEditar: () -> Unit) {
    val emoji = iconoMap[categoria.icono] ?: categoria.icono
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
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(emoji, style = MaterialTheme.typography.titleMedium)
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    categoria.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    if (categoria.activa) "Activa" else "Inactiva",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (categoria.activa) Color(0xFF16A34A) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = categoria.activa,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF7C3AED))
            )
            OutlinedButton(
                onClick = onEditar,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Editar", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
