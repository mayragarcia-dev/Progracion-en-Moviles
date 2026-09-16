package com.garcia.lab03registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.graphics.Brush
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

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen() {

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

    var redondearPromedio by remember {
        mutableStateOf(false)
    }

    var notasConfirmadas by remember {
        mutableStateOf(false)
    }

    var calcularPromedio by remember {
        mutableStateOf(false)
    }

    var promedioPonderado by remember {
        mutableFloatStateOf(0f)
    }

    var promedioFinal by remember {
        mutableFloatStateOf(0f)
    }

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
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF9F5FA),
                            Color(0xFFEFE4F5)
                        )
                    )
                )
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
            ) {

                Text(
                    text = "Notas del ciclo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Desliza para asignar cada nota (0 a 20)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                NotaCurso(
                    nombre = "Fundamentos de Programación",
                    peso = "20%",
                    nota = notaFundamentos,
                    onNotaChange = {
                        notaFundamentos = it
                        calcularPromedio = false
                    }
                )

                NotaCurso(
                    nombre = "Programación Orientada a Objetos",
                    peso = "25%",
                    nota = notaPOO,
                    onNotaChange = {
                        notaPOO = it
                        calcularPromedio = false
                    }
                )

                NotaCurso(
                    nombre = "Programación en Móviles",
                    peso = "30%",
                    nota = notaMoviles,
                    onNotaChange = {
                        notaMoviles = it
                        calcularPromedio = false
                    }
                )

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
                    modifier = Modifier.height(14.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Redondear promedio final",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF333333)
                    )

                    Switch(
                        checked = redondearPromedio,
                        onCheckedChange = {
                            redondearPromedio = it
                            calcularPromedio = false
                        },
                        colors = androidx.compose.material3.SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF6A1B9A),
                            uncheckedThumbColor = Color(0xFF777777),
                            uncheckedTrackColor = Color(0xFFE0E0E0)
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = notasConfirmadas,
                        onCheckedChange = {
                            notasConfirmadas = it
                            calcularPromedio = false
                        },
                        colors = androidx.compose.material3.CheckboxDefaults.colors(
                            checkedColor = Color(0xFF6A1B9A),
                            uncheckedColor = Color(0xFF999999),
                            checkmarkColor = Color.White
                        )
                    )

                    Text(
                        text = "Confirmo que las notas son correctas",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF333333)
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {

                        promedioPonderado =
                            (notaFundamentos * 0.20f) +
                                    (notaPOO * 0.25f) +
                                    (notaMoviles * 0.30f) +
                                    (notaBaseDatos * 0.25f)

                        promedioFinal =
                            if (redondearPromedio) {
                                promedioPonderado.roundToInt().toFloat()
                            } else {
                                promedioPonderado
                            }

                        calcularPromedio = true
                    },
                    enabled = notasConfirmadas,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A1B9A),
                        disabledContainerColor = Color(0xFFBDBDBD),
                        disabledContentColor = Color.White
                    )
                ) {

                    Text(
                        text = "CALCULAR PROMEDIO",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                if (!calcularPromedio) {

                    Text(
                        text = "Asigna las notas y confirma para calcular",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF777777),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (calcularPromedio) {

                    val observacion = when {
                        promedioFinal >= 17f -> "EXCELENTE"
                        promedioFinal >= 13f -> "APROBADO"
                        promedioFinal >= 10f -> "EN RECUPERACIÓN"
                        else -> "DESAPROBADO"
                    }

                    val colorChip = when {
                        promedioFinal >= 17f -> Color(0xFF2E7D32)
                        promedioFinal >= 13f -> Color(0xFF43A047)
                        promedioFinal >= 10f -> Color(0xFFFFB300)
                        else -> Color(0xFFC62828)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Promedio ponderado: %.2f"
                                    .format(promedioPonderado),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF333333)
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = if (redondearPromedio) {
                                    "Promedio final: %.0f"
                                        .format(promedioFinal)
                                } else {
                                    "Promedio final: %.2f"
                                        .format(promedioFinal)
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6A1B9A)
                            )

                            if (redondearPromedio) {

                                Text(
                                    text = "(redondeado)",
                                    color = Color(0xFF777777)
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorChip
                                )
                            ) {

                                Text(
                                    text = observacion,
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 6.dp
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "✓ Promedio calculado correctamente",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "Desarrollado por: Mayra Julisa Garcia Rojas",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                textAlign = TextAlign.Center,
                color = Color(0xFF666666)
            )
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
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
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = nombre,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = "($peso)",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666)
            )
        }

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Slider(
                value = nota,
                onValueChange = { nuevoValor ->
                    val notaEntera = nuevoValor
                        .roundToInt()
                        .coerceIn(0, 20)

                    onNotaChange(notaEntera.toFloat())
                },
                valueRange = 0f..20f,
                steps = 19,
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(
                                color = Color(0xFF6A1B9A),
                                shape = CircleShape
                            )
                    )
                },
                track = { sliderState ->

                    SliderDefaults.Track(
                        sliderState = sliderState,
                        modifier = Modifier.height(3.dp),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF6A1B9A),
                            inactiveTrackColor = Color(0xFFD8C9E8)
                        ),
                        thumbTrackGapSize = 0.dp,
                        drawStopIndicator = null
                    )
                }
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Box(
                modifier = Modifier
                    .width(42.dp)
                    .height(32.dp)
                    .background(
                        color = Color(0xFFEDE3F8),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = nota.toInt().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A1B9A)
                )
            }
        }
    }
}