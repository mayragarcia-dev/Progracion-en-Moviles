package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

@Composable
fun CategoriasScreen(vm: CategoriasViewModel, onEditar: (Int) -> Unit) {
    val categorias by vm.categorias.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
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

@Composable
private fun CategoriaItem(categoria: Categoria, onToggle: () -> Unit, onEditar: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(categoria.icono, style = MaterialTheme.typography.titleMedium)
            Column(modifier = Modifier.weight(1f)) {
                Text(categoria.nombre, style = MaterialTheme.typography.titleMedium)
                Text(if (categoria.activa) "Activa" else "Inactiva", style = MaterialTheme.typography.bodySmall)
            }
            Switch(checked = categoria.activa, onCheckedChange = { onToggle() })
            OutlinedButton(onClick = onEditar) { Text("Editar") }
        }
    }
}
