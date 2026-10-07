package com.tecsup.mibodega.ui.cliente.screens.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.AzulEnlace
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.FondoClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 1: Registro / Login (mockup "Cliente").
 * No sabe navegar sola: recibe qué hacer por parámetro (callbacks).
 */
@Composable
fun BienvenidaScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onRegistrarse: () -> Unit,
    onIniciarSesion: () -> Unit,
    onTerminos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FondoClaro, MaterialTheme.colorScheme.background),
                    endY = 900f
                )
            )
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botón de alternancia modo claro/oscuro en la parte superior derecha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onToggleDarkMode) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.DarkMode,
                    contentDescription = "Cambiar modo claro/oscuro",
                    tint = if (isDarkMode) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(Modifier.weight(0.3f))

        IlustracionBodega()

        Spacer(Modifier.height(16.dp))

        TituloMiBodega()

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Tus productos de siempre\nen la puerta de tu casa",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Registrarme",
            subtexto = "con mi teléfono",
            icono = painterResource(R.drawable.ic_whatsapp),
            onClick = onRegistrarse
        )

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Iniciar sesión",
            onClick = onIniciarSesion
        )

        Spacer(Modifier.height(20.dp))

        PieTerminos(onTerminos = onTerminos)

        Spacer(Modifier.weight(0.5f))
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun IlustracionBodega() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(170.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ilustracion_bodega),
                    contentDescription = "Ilustración de la bodega",
                    modifier = Modifier.size(130.dp)
                )
            }
        }
    }
}

@Composable
private fun TituloMiBodega() {
    Text(
        text = buildAnnotatedString {
            append("Mi ")
            withStyle(SpanStyle(color = VerdeBodega)) { append("Bodega") }
        },
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun PieTerminos(onTerminos: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Al continuar aceptas nuestros",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Términos y Condiciones",
            style = MaterialTheme.typography.bodySmall,
            color = AzulEnlace,
            modifier = Modifier.clickable(onClick = onTerminos)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BienvenidaPreview() {
    BodegaTheme {
        BienvenidaScreen(false, {}, {}, {}, {})
    }
}
