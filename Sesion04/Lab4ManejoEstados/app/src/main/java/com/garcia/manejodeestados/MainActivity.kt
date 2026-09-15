package com.garcia.manejodeestados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Modelo de datos para representar una Tarea.
 */
data class Tarea(
    val id: Int,
    val nombre: String,
    val completada: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaTareasTecsup()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareasTecsup() {
    // Estado para el texto que se ingresa en el TextField
    var textoTarea by remember { mutableStateOf("") }
    
    // Estado para generar IDs únicos
    var contadorId by remember { mutableStateOf(1) }
    
    // Estado para la lista mutable de tareas
    val listaTareas = remember { mutableStateListOf<Tarea>() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de tareas - Tecsup") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Sección de ingreso de tareas
            OutlinedTextField(
                value = textoTarea,
                onValueChange = { textoTarea = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (textoTarea.isNotBlank()) {
                        listaTareas.add(
                            Tarea(
                                id = contadorId,
                                nombre = textoTarea
                            )
                        )
                        contadorId++
                        textoTarea = "" // Limpiar el campo
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar tarea")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contador de tareas
            Text(
                text = "Total de tareas: ${listaTareas.size}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de tareas
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = listaTareas,
                    key = { it.id } // Usar el ID como llave para optimizar la lista
                ) { tarea ->
                    ItemTarea(
                        tarea = tarea,
                        onEliminar = {
                            listaTareas.remove(tarea)
                        },
                        onCambiarEstado = { completada ->
                            val index = listaTareas.indexOf(tarea)
                            if (index != -1) {
                                // En Compose, para que el estado de un item en mutableStateListOf se refleje, 
                                // debemos reemplazar la instancia o usar una propiedad mutable. 
                                // Aquí reemplazamos el objeto.
                                listaTareas[index] = listaTareas[index].copy(completada = completada)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemTarea(
    tarea: Tarea,
    onEliminar: () -> Unit,
    onCambiarEstado: (Boolean) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { onCambiarEstado(it) }
            )
            
            Text(
                text = tarea.nombre,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaTareas() {
    MaterialTheme {
        PantallaTareasTecsup()
    }
}
