package com.garcia.lab03registroproducto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun RegistroProductoScreen() {

    var nombreProducto by remember {
        mutableStateOf("")
    }

    var precio by remember {
        mutableStateOf("")
    }

    var cantidad by remember {
        mutableStateOf("")
    }

    var mostrarResumen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Registro de Producto",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Ingrese los datos del producto"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = nombreProducto,
            onValueChange = { nombreProducto = it },
            label = {
                Text("Nombre del producto:")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = {
                    Text("Precio:")
                },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = {
                    Text("Cantidad:")
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                mostrarResumen = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("AGREGAR PRODUCTO")
        }

        if (mostrarResumen) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            val precioNumero = precio.toDoubleOrNull() ?: 0.0
            val cantidadNumero = cantidad.toIntOrNull() ?: 0
            val importe = precioNumero * cantidadNumero

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Resumen del producto",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "Producto: $nombreProducto"
                    )

                    Text(
                        text = "Precio: S/ ${String.format("%.2f", precioNumero)}"
                    )

                    Text(
                        text = "Cantidad: $cantidadNumero"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "Importe: S/ ${String.format("%.2f", importe)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}