package com.garcia.manejodeestados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                PantallaTareas()
            }
        }
    }
}

@Composable
fun TemperatureDisplay(modifier: Modifier = Modifier) {
    var temperatura by remember { mutableStateOf(20) }

    val colorTexto = when {
        temperatura > 30 -> Color.Red
        temperatura < 10 -> Color.Blue
        else -> Color.Unspecified
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Temperatura: $temperatura °C",
                style = MaterialTheme.typography.titleLarge,
                color = colorTexto
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { temperatura++ }) {
                    Text("Subir")
                }
                Button(onClick = { temperatura-- }) {
                    Text("Bajar")
                }
                Button(onClick = { temperatura = 20 }) {
                    Text("Resetear")
                }
            }
        }
    }
}

@Composable
fun PantallaTareas() {
    var textoTarea by remember { mutableStateOf("") }
    var contadorId by remember { mutableStateOf(1) }
    val listaTareas = remember { mutableStateListOf<Tarea>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TemperatureDisplay()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lista de tareas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textoTarea,
            onValueChange = { textoTarea = it },
            label = { Text("Ingrese una tarea") },
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
                    textoTarea = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de tareas: ${listaTareas.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(listaTareas, key = { it.id }) { tarea ->
                ItemTarea(
                    tarea = tarea,
                    onEliminar = {
                        listaTareas.remove(tarea)
                    },
                    onCambiarEstado = { completada ->
                        val index = listaTareas.indexOf(tarea)
                        if (index != -1) {
                            listaTareas[index] = listaTareas[index].copy(completada = completada)
                        }
                    }
                )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tarea.completada,
                    onCheckedChange = {
                        onCambiarEstado(it)
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = tarea.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(onClick = onEliminar) {
                Text("Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaTareas() {
    MaterialTheme {
        PantallaTareas()
    }
}