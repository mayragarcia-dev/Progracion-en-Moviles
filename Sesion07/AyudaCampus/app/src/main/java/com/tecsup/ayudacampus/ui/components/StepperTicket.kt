package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val EstadosPaso = listOf("Abierto", "En proceso", "Resuelto", "Cerrado")
private val CianPaso = Color(0xFF0891B2)

@Composable
fun StepperTicket(estado: String, modifier: Modifier = Modifier) {
    val pasoActual = when (estado) {
        "ABIERTO" -> 0
        "EN_PROCESO" -> 1
        "RESUELTO" -> 2
        "CERRADO" -> 3
        else -> 0
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            EstadosPaso.indices.forEach { indice ->
                val completado = indice <= pasoActual
                Box(
                    modifier = Modifier.size(20.dp).background(
                        if (completado) CianPaso else MaterialTheme.colorScheme.outlineVariant,
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (indice < pasoActual || estado == "CERRADO" && indice == pasoActual) "✓" else "",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (indice < EstadosPaso.lastIndex) {
                    Spacer(
                        Modifier.weight(1f).height(3.dp).background(
                            if (indice < pasoActual) CianPaso else MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }
            }
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            EstadosPaso.forEach { etiqueta ->
                Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
