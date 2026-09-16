package com.tecsup.garcia

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TarjetaProducto(producto: Producto) {

    Card {
        Text(
            text = producto.nombre
        )
    }
}
