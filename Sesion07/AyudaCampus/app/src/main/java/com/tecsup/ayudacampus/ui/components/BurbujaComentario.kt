package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Comentario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val FondoEstudiante = Color(0xFFEDE9FE)
private val FondoSoporte = Color(0xFFE0F2FE)

@Composable
fun BurbujaComentario(comentario: Comentario, propio: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (propio) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (propio) FondoEstudiante else FondoSoporte,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = if (propio) 4.dp else 16.dp,
                bottomStart = if (propio) 16.dp else 4.dp
            ),
            modifier = Modifier.fillMaxWidth(.82f)
        ) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 9.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(comentario.autorNombre, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                    Text(" · ${comentario.autorRol}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(comentario.texto, style = MaterialTheme.typography.bodyMedium)
                Text(
                    SimpleDateFormat("dd/MM · HH:mm", Locale.getDefault()).format(Date(comentario.fecha)),
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
