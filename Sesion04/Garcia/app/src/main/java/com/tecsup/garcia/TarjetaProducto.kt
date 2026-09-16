package com.tecsup.garcia

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun TarjetaProducto(producto: Producto) {

    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.nombre
            )

            Text(
                text = "S/ ${producto.precio} x ${producto.cantidad}"
            )
        }
    }
}
