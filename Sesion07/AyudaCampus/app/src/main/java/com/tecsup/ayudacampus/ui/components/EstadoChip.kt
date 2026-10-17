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
fun EstadoChip(estado: String, modifier: Modifier = Modifier) {
    val (fondo, color, etiqueta) = when (estado) {
        "ABIERTO" -> Triple(Color(0xFFFFF1CC), Color(0xFFB45309), "Abierto")
        "EN_PROCESO" -> Triple(Color(0xFFDBEAFE), Color(0xFF2563EB), "En proceso")
        "RESUELTO" -> Triple(Color(0xFFDCFCE7), Color(0xFF16A34A), "Resuelto")
        "CERRADO" -> Triple(Color(0xFFF3F4F6), Color(0xFF6B7280), "Cerrado")
        else -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, estado)
    }
    Box(modifier.background(fondo, RoundedCornerShape(50)).padding(horizontal = 9.dp, vertical = 4.dp)) {
        Text(etiqueta, color = color, style = MaterialTheme.typography.labelSmall)
    }
}
