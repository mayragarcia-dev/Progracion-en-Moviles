package com.garcia.lab03registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.garcia.lab03registronotas.ui.theme.Lab03RegistroNotasTheme
import kotlin.math.roundToInt

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen() {

    // ==========================================
    // NOTAS DE LOS CURSOS
    // ==========================================

    var notaFundamentos by remember {
        mutableFloatStateOf(0f)
    }

    var notaPOO by remember {
        mutableFloatStateOf(0f)
    }

    var notaMoviles by remember {
        mutableFloatStateOf(0f)
    }

    var notaBaseDatos by remember {
        mutableFloatStateOf(0f)
    }

    // ==========================================
    // OPCIONES
    // ==========================================

    var redondearPromedio by remember {
        mutableStateOf(false)
    }

    var notasConfirmadas by remember {
        mutableStateOf(false)
    }

    // ==========================================
    // RESULTADOS
    // ==========================================

    var calcularPromedio by remember {
        mutableStateOf(false)
    }

    var promedioPonderado by remember {
        mutableFloatStateOf(0f)
    }

    var promedioFinal by remember {
        mutableFloatStateOf(0f)
    }

    // ==========================================
    // PANTALLA PRINCIPAL
    // ==========================================

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Registro de Notas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A1B9A),
                    titleContentColor = Color.White
                )
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F7))
                .padding(innerPadding)
        ) {

            // ==========================================
            // CONTENIDO
            // ==========================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(16.dp)
            ) {

                // ==========================================
                // TÍTULO DE LA SECCIÓN
                // ==========================================

                Text(
                    text = "Notas del ciclo",

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF222222)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Desliza para asignar cada nota (0 a 20)",

                    style = MaterialTheme.typography.bodyMedium,

                    color = Color(0xFF555555)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // ==========================================
                // CURSO 1
                // ==========================================

                NotaCurso(
                    nombre = "Fundamentos de Programación",
                    peso = "20%",
                    nota = notaFundamentos,

                    onNotaChange = {
                        notaFundamentos = it
                        calcularPromedio = false
                    }
                )

                // ==========================================
                // CURSO 2
                // ==========================================

                NotaCurso(
                    nombre = "Programación Orientada a Objetos",
                    peso = "25%",
                    nota = notaPOO,

                    onNotaChange = {
                        notaPOO = it
                        calcularPromedio = false
                    }
                )

                // ==========================================
                // CURSO 3
                // ==========================================

                NotaCurso(
                    nombre = "Programación en Móviles",
                    peso = "30%",
                    nota = notaMoviles,

                    onNotaChange = {
                        notaMoviles = it
                        calcularPromedio = false
                    }
                )

                // ==========================================
                // CURSO 4
                // ==========================================

                NotaCurso(
                    nombre = "Base de Datos",
                    peso = "25%",
                    nota = notaBaseDatos,

                    onNotaChange = {
                        notaBaseDatos = it
                        calcularPromedio = false
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // ==========================================
                // REDONDEAR PROMEDIO FINAL
                // ==========================================

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Redondear promedio final",

                        color = Color(0xFF222222),

                        style = MaterialTheme.typography.bodyLarge,

                        fontWeight = FontWeight.Bold
                    )

                    Switch(
                        checked = redondearPromedio,

                        onCheckedChange = {
                            redondearPromedio = it
                            calcularPromedio = false
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                // ==========================================
                // CONFIRMACIÓN DE LAS NOTAS
                // ==========================================

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = notasConfirmadas,

                        onCheckedChange = {
                            notasConfirmadas = it
                            calcularPromedio = false
                        }
                    )

                    Text(
                        text = "Confirmo que las notas son correctas",

                        color = Color(0xFF222222),

                        style = MaterialTheme.typography.bodyLarge,

                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // ==========================================
                // BOTÓN CALCULAR PROMEDIO
                // ==========================================

                Button(

                    onClick = {

                        promedioPonderado =
                            (notaFundamentos * 0.20f) +
                                    (notaPOO * 0.25f) +
                                    (notaMoviles * 0.30f) +
                                    (notaBaseDatos * 0.25f)

                        promedioFinal =
                            if (redondearPromedio) {

                                promedioPonderado
                                    .roundToInt()
                                    .toFloat()

                            } else {

                                promedioPonderado
                            }

                        calcularPromedio = true
                    },

                    enabled = notasConfirmadas,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(

                        containerColor = Color(0xFF7B1FA2),

                        contentColor = Color.White,

                        disabledContainerColor = Color(0xFFE0E0E0),

                        disabledContentColor = Color(0xFF9E9E9E)
                    )
                ) {

                    Text(
                        text = "CALCULAR PROMEDIO",

                        color = Color.White,

                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // ==========================================
                // MENSAJE ANTES DEL CÁLCULO
                // ==========================================

                if (!calcularPromedio) {

                    Text(
                        text = "Asigna las notas y confirma para calcular",

                        modifier = Modifier.fillMaxWidth(),

                        color = Color(0xFF666666),

                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // ==========================================
                // RESULTADO
                // ==========================================

                if (calcularPromedio) {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            // ----------------------------------
                            // TÍTULO RESULTADO
                            // ----------------------------------

                            Text(
                                text = "Resultado",

                                style = MaterialTheme.typography.titleLarge,

                                fontWeight = FontWeight.Bold,

                                color = Color(0xFF222222)
                            )

                            Spacer(
                                modifier = Modifier.height(14.dp)
                            )

                            // ----------------------------------
                            // PROMEDIO PONDERADO
                            // ----------------------------------

                            Text(
                                text = "Promedio ponderado: %.2f"
                                    .format(promedioPonderado),

                                style = MaterialTheme.typography.bodyLarge,

                                color = Color(0xFF333333)
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            // ----------------------------------
                            // PROMEDIO FINAL
                            // ----------------------------------

                            if (redondearPromedio) {

                                Text(
                                    text = "Promedio final: %.0f"
                                        .format(promedioFinal),

                                    style = MaterialTheme.typography.titleMedium,

                                    fontWeight = FontWeight.Bold,

                                    color = Color(0xFF222222)
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = "Resultado redondeado",

                                    style = MaterialTheme.typography.bodySmall,

                                    color = Color(0xFF666666)
                                )

                            } else {

                                Text(
                                    text = "Promedio final: %.2f"
                                        .format(promedioFinal),

                                    style = MaterialTheme.typography.titleMedium,

                                    fontWeight = FontWeight.Bold,

                                    color = Color(0xFF222222)
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            // ----------------------------------
                            // ESTADO
                            // ----------------------------------

                            val aprobado = promedioFinal >= 11f

                            Card(

                                shape = RoundedCornerShape(20.dp),

                                colors = CardDefaults.cardColors(

                                    containerColor =
                                        if (aprobado) {
                                            Color(0xFFE8F5E9)
                                        } else {
                                            Color(0xFFFFEBEE)
                                        }
                                )
                            ) {

                                Text(

                                    text =
                                        if (aprobado) {
                                            "APROBADO"
                                        } else {
                                            "DESAPROBADO"
                                        },

                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),

                                    fontWeight = FontWeight.Bold,

                                    color =
                                        if (aprobado) {
                                            Color(0xFF2E7D32)
                                        } else {
                                            Color(0xFFC62828)
                                        }
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    // ==========================================
                    // MENSAJE DE ÉXITO
                    // ==========================================

                    Text(
                        text = "✓ Promedio calculado correctamente",

                        modifier = Modifier.fillMaxWidth(),

                        color = Color(0xFF2E7D32),

                        style = MaterialTheme.typography.bodyLarge,

                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }

            // ==========================================
            // PIE DE PÁGINA
            // ==========================================

            Text(
                text = "Desarrollado por: Mayra Garcia",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 12.dp
                    ),

                textAlign = TextAlign.Center,

                style = MaterialTheme.typography.bodyMedium,

                color = Color(0xFF666666),

                fontWeight = FontWeight.Medium
            )
        }
    }
}


// ==========================================================
// COMPONENTE PARA CADA CURSO
// ==========================================================

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

        // ==========================================
        // NOMBRE Y PESO
        // ==========================================

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = nombre,

                modifier = Modifier.weight(1f),

                style = MaterialTheme.typography.bodyLarge,

                fontWeight = FontWeight.Medium,

                color = Color(0xFF222222)
            )

            Spacer(
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Text(
                text = peso,

                style = MaterialTheme.typography.bodyMedium,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF555555)
            )
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        // ==========================================
        // SLIDER Y NOTA
        // ==========================================

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

            Spacer(
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Text(
                text = nota.roundToInt().toString(),

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF6A1B9A)
            )
        }
    }
}