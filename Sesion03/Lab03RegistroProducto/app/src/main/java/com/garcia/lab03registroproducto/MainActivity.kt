package com.garcia.lab03registroproducto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.garcia.lab03registroproducto.ui.theme.Lab03RegistroProductoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab03RegistroProductoTheme {
                RegistroProductoScreen()
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun RegistroProductoScreen() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Registro de Producto",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Ingresa los datos del producto"
        )
    }
}