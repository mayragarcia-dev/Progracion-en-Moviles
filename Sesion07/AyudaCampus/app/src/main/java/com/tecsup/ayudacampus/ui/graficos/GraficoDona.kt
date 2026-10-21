package com.tecsup.ayudacampus.ui.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.DatoGrafico

@Composable
fun GraficoDona(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val total = datos.sumOf { it.valor }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(132.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(Modifier.size(116.dp)) {
                    val grosor = 18.dp.toPx()
                    if (total == 0) {
                        drawArc(Color(0xFFE5E7EB), 0f, 360f, false, style = Stroke(grosor))
                    } else {
                        var angulo = -90f
                        datos.filter { it.valor > 0 }.forEach { dato ->
                            val barrido = 360f * dato.valor / total
                            drawArc(Color(dato.color.toInt()), angulo, barrido, false, style = Stroke(grosor))
                            angulo += barrido
                        }
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("$total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("tickets", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(Modifier.width(24.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                datos.forEach { dato ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(10.dp).background(Color(dato.color.toInt()), CircleShape))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            dato.etiqueta,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            dato.valor.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
