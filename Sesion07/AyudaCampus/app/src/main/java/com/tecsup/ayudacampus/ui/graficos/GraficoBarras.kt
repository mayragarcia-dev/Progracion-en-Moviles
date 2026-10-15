package com.tecsup.ayudacampus.ui.graficos

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.DatoGrafico

@Composable
fun GraficoBarras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) {
    val maximo = datos.maxOfOrNull { it.valor }?.coerceAtLeast(1) ?: 1
    Surface(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 1.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp).padding(horizontal = 12.dp, vertical = 12.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            datos.forEach { dato ->
                Column(
                    modifier = Modifier.width(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(dato.valor.toString(), style = MaterialTheme.typography.labelSmall)
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier.width(32.dp)
                            .height((82f * dato.valor / maximo).coerceAtLeast(if (dato.valor == 0) 3f else 12f).dp)
                            .background(Color(dato.color.toULong()), RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(dato.etiqueta, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}
