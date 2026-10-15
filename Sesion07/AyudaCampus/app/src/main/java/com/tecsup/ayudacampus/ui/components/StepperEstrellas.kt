package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun StepperEstrellas(
    calificacion: Int,
    onSeleccionar: (Int) -> Unit,
    habilitado: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        (1..5).forEach { valor ->
            val seleccionada = valor <= calificacion
            Text(
                text = if (seleccionada) "★" else "☆",
                modifier = Modifier
                    .semantics { contentDescription = "$valor de 5 estrellas" }
                    .then(if (habilitado) Modifier.clickable { onSeleccionar(valor) } else Modifier),
                color = if (seleccionada) Color(0xFFF59E0B) else MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
