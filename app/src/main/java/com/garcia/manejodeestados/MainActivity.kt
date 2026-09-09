package com.garcia.manejodeestados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.garcia.manejodeestados.ui.theme.ManejoDeEstadosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManejoDeEstadosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Paso2MutableState(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Paso2MutableState(modifier: Modifier = Modifier) {
    // Estado observable para el texto del campo de entrada
    var nombreInput by remember { mutableStateOf("") }

    // Estado observable para el mensaje de saludo guardado
    var mensajeSaludo by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Paso 2: mutableStateOf",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // El OutlinedTextField lee y actualiza el estado 'nombreInput' en tiempo real
        OutlinedTextField(
            value = nombreInput,
            onValueChange = { nuevoTexto -> nombreInput = nuevoTexto },
            label = { Text("Escribe tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombreInput.isNotBlank()) {
                    mensajeSaludo = "¡Hola, $nombreInput! Bienvenido al Paso 2."
                    nombreInput = "" // Limpiamos el campo de entrada
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Saludar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Este Text se recompone automáticamente cuando 'mensajeSaludo' cambia
        if (mensajeSaludo.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = mensajeSaludo,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}