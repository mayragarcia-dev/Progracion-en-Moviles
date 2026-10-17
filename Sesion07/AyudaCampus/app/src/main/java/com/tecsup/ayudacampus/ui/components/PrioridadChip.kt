package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrioridadChip(prioridad: String, modifier: Modifier = Modifier) {
    val color = when (prioridad) {
        "ALTA" -> Color(0xFFDC2626)
        "MEDIA" -> Color(0xFFB45309)
        else -> Color(0xFF6B7280)
    }
    Box(modifier.background(color.copy(alpha = .12f), RoundedCornerShape(50)).padding(horizontal = 9.dp, vertical = 4.dp)) {
        Text(prioridad.lowercase().replaceFirstChar { it.uppercase() }, color = color, style = MaterialTheme.typography.labelSmall)
    }
}
