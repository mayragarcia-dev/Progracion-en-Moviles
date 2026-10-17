package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.model.TicketResumen

@Composable
fun TicketCard(
    ticket: TicketResumen,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarCliente: Boolean = false
) {
    val colorPrioridad = when (ticket.prioridad) {
        "ALTA" -> Color(0xFFDC2626)
        "MEDIA" -> Color(0xFFB45309)
        else -> Color(0xFF6B7280)
    }
    val (colorEstado, fondoEstado, etiquetaEstado) = when (ticket.estado) {
        "ABIERTO" -> Triple(Color(0xFFB45309), Color(0xFFFFF1CC), "Abierto")
        "EN_PROCESO" -> Triple(Color(0xFF2563EB), Color(0xFFDBEAFE), "En proceso")
        "RESUELTO" -> Triple(Color(0xFF16A34A), Color(0xFFDCFCE7), "Resuelto")
        else -> Triple(Color(0xFF6B7280), Color(0xFFF3F4F6), "Cerrado")
    }
    val simbolo = when (ticket.categoriaIcono.lowercase()) {
        "wifi" -> "⌁"
        "code" -> "</>"
        "key" -> "⚿"
        "printer" -> "▤"
        else -> "▣"
    }

    Surface(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier.height(94.dp)) {
            Box(Modifier.width(4.dp).fillMaxHeight().background(colorPrioridad))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.width(38.dp).height(38.dp)
                        .background(Color(0xFFD9F7FA), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(simbolo, color = Color(0xFF0891B2), fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                    Text(ticket.titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        if (mostrarCliente) "#${ticket.id.toString().padStart(4, '0')} · ${ticket.clienteNombre}"
                        else "#${ticket.id.toString().padStart(4, '0')} · ${ticket.ubicacion}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(7.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        EtiquetaTicket(etiquetaEstado, fondoEstado, colorEstado)
                        EtiquetaTicket(ticket.prioridad.lowercase().replaceFirstChar { it.uppercase() }, colorPrioridad.copy(alpha = .12f), colorPrioridad)
                    }
                }
                Spacer(Modifier.width(5.dp))
                Text(
                    tiempoRelativo(ticket.fecha),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EtiquetaTicket(texto: String, fondo: Color, textoColor: Color) {
    Box(
        modifier = Modifier.background(fondo, RoundedCornerShape(50)).padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(texto, color = textoColor, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
    }
}

private fun tiempoRelativo(fecha: Long): String {
    val minutos = ((System.currentTimeMillis() - fecha).coerceAtLeast(0L) / 60_000L).toInt()
    return when {
        minutos < 1 -> "Ahora"
        minutos < 60 -> "Hace ${minutos} min"
        minutos < 1_440 -> "Hace ${minutos / 60} h"
        else -> "Hace ${minutos / 1_440} d"
    }
}
