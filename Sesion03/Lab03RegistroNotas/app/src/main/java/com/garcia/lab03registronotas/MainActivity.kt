package com.garcia.lab03registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.garcia.lab03registronotas.ui.theme.Lab03RegistroNotasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab03RegistroNotasTheme {
                RegistroNotasScreen()
            }
        }
    }
}

@Composable
fun RegistroNotasScreen() {

    var notaFundamentos by remember { mutableFloatStateOf(0f) }
    var notaPOO by remember { mutableFloatStateOf(0f) }
    var notaMoviles by remember { mutableFloatStateOf(0f) }
    var notaBaseDatos by remember { mutableFloatStateOf(0f) }

    var redondearPromedio by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Registro de Notas",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            NotaCurso(
                nombre = "Fundamentos de Programación",
                peso = "20%",
                nota = notaFundamentos,
                onNotaChange = { notaFundamentos = it }
            )

            NotaCurso(
                nombre = "Programación Orientada a Objetos",
                peso = "25%",
                nota = notaPOO,
                onNotaChange = { notaPOO = it }
            )

            NotaCurso(
                nombre = "Programación en Móviles",
                peso = "30%",
                nota = notaMoviles,
                onNotaChange = { notaMoviles = it }
            )

            NotaCurso(
                nombre = "Base de Datos",
                peso = "25%",
                nota = notaBaseDatos,
                onNotaChange = { notaBaseDatos = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Redondear promedio final"
                )

                Switch(
                    checked = redondearPromedio,
                    onCheckedChange = {
                        redondearPromedio = it
                    }
                )
            }
        }
    }
}

@Composable
fun NotaCurso(
    nombre: String,
    peso: String,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = peso,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Slider(
                value = nota,
                onValueChange = onNotaChange,
                valueRange = 0f..20f,
                steps = 19,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = nota.toInt().toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}