package com.garcia.lab04manejoestados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.garcia.lab04manejoestados.ui.theme.Lab04ManejoEstadosTheme

data class Tarea(
    val id: Int,
    val nombre: String,
    val completada: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab04ManejoEstadosTheme {
                PantallaTareas()
            }
        }
    }
}

@Composable
fun TrashIcon(tint: Color, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.size(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(2.dp)
                .background(tint, shape = RoundedCornerShape(1.dp))
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(16.dp)
                .border(2.dp, tint, shape = RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp))
                .padding(horizontal = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier.fillMaxHeight().width(1.5.dp).background(tint))
                Box(modifier = Modifier.fillMaxHeight().width(1.5.dp).background(tint))
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
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tarea.completada,
                    onCheckedChange = {
                        onCambiarEstado(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF5E4E9E),
                        uncheckedColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = tarea.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (tarea.completada) Color.Gray else Color.Black,
                    textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            IconButton(onClick = onEliminar) {
                TrashIcon(tint = Color(0xFF90CAF9))
            }
        }
    }
}

@Composable
fun PantallaTareas() {
    var textoTarea by remember { mutableStateOf("") }
    var contadorId by remember { mutableIntStateOf(1) }
    val listaTareas = remember { mutableStateListOf<Tarea>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lista de tareas - Tecsup",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textoTarea,
            onValueChange = { textoTarea = it },
            label = { Text("Ingrese una tarea") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

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
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E4E9E)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Agregar tarea",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de tareas: ${listaTareas.size}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                listaTareas,
                key = { it.id }
            ) { tarea ->
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

@Preview(showBackground = true)
@Composable
fun PreviewPantallaTareas() {
    Lab04ManejoEstadosTheme {
        PantallaTareas()
    }
}