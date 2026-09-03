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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
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

    var mensajeError by remember {
        mutableStateOf("")
    }

    val azul = Color(0xFF3F51B5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registrar Producto",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Nuevo producto",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Completa los datos y presiona Agregar"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(
                value = nombreProducto,
                onValueChange = {
                    nombreProducto = it
                    mensajeError = ""
                },
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
                    onValueChange = {
                        precio = it
                        mensajeError = ""
                    },
                    label = {
                        Text("Precio:")
                    },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = {
                        cantidad = it
                        mensajeError = ""
                    },
                    label = {
                        Text("Cantidad:")
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = {
                        val precioNumero = precio.replace(",", ".").toDoubleOrNull()
                        val cantidadNumero = cantidad.toIntOrNull()

                        when {
                            nombreProducto.isBlank() -> {
                                mensajeError = "Ingresa el nombre del producto"
                                mostrarResumen = false
                            }

                            precioNumero == null || precioNumero <= 0 -> {
                                mensajeError = "Ingresa un precio válido mayor que 0"
                                mostrarResumen = false
                            }

                            cantidadNumero == null || cantidadNumero <= 0 -> {
                                mensajeError = "Ingresa una cantidad válida mayor que 0"
                                mostrarResumen = false
                            }

                            else -> {
                                mensajeError = ""
                                mostrarResumen = true
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("AGREGAR PRODUCTO")
                }

                Button(
                    onClick = {
                        nombreProducto = ""
                        precio = ""
                        cantidad = ""
                        mensajeError = ""
                        mostrarResumen = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("LIMPIAR")
                }
            }

            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = mensajeError,
                    color = Color.Red
                )
            }

            if (mostrarResumen) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                val precioNumero = precio.replace(",", ".").toDoubleOrNull() ?: 0.0
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
                            text = "Precio: S/ ${
                                String.format(
                                    "%.2f",
                                    precioNumero
                                )
                            }"
                        )

                        Text(
                            text = "Cantidad: $cantidadNumero"
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Importe: S/ ${
                                String.format(
                                    "%.2f",
                                    importe
                                )
                            }",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "✓ Producto registrado correctamente",
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}
