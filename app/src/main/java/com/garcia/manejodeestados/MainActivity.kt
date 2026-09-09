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
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ContadorRoto()
                    }
                }
            }
        }
    }
}

@Composable
fun ContadorRoto() {
    var contador = 0 // ⚠ Se resetea a 0 en cada recomposición
    Column {
        Text("Contador: $contador")
        Button(onClick = { contador++ }) { // No causa recomposición
            Text("Incrementar")
        }
    }
}
