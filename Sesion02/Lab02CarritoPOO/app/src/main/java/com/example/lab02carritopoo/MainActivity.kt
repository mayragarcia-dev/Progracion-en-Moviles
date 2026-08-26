package com.example.lab02carritopoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab02carritopoo.ui.theme.Lab02CarritoPOOTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab02CarritoPOOTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PruebaPOO(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PruebaPOO(modifier: Modifier = Modifier) {

    val carrito = Carrito()

    val producto1 = Producto(
        "Producto general",
        100.0,
        1
    )

    val producto2 = ProductoElectronico(
        "Laptop HP",
        2500.0,
        1,
        "HP"
    )

    val producto3 = ProductoElectronico(
        "Laptop Lenovo",
        3500.0,
        1,
        "Lenovo"
    )

    carrito.agregarProducto(producto1)
    carrito.agregarProducto(producto2)
    carrito.agregarProducto(producto3)

    val subtotal = carrito.calcularSubtotal()
    val igv = carrito.calcularIGV()
    val total = carrito.calcularTotal()
    val descuento = carrito.calcularDescuento()
    val totalFinal = carrito.calcularTotalConDescuento()
    val masCaro = carrito.obtenerProductoMasCaro()

    val texto = buildString {

        appendLine("===== CARRITO DE COMPRAS =====")
        appendLine()

        for (producto in carrito.obtenerProductos()) {

            appendLine(
                "${producto.getNombre()} - " +
                        "S/ ${"%.2f".format(producto.getPrecio())} " +
                        "x ${producto.getCantidad()}"
            )

            appendLine(producto.mostrarTipo())
            appendLine()
        }

        appendLine("------------------------------")
        appendLine("Subtotal: S/ %.2f".format(subtotal))
        appendLine("IGV (18%%): S/ %.2f".format(igv))
        appendLine("Total: S/ %.2f".format(total))
        appendLine("Descuento: S/ %.2f".format(descuento))
        appendLine("Total final: S/ %.2f".format(totalFinal))
        appendLine()

        if (masCaro != null) {
            appendLine(
                "Producto más caro: " +
                        "${masCaro.getNombre()} - " +
                        "S/ ${"%.2f".format(masCaro.getPrecio())}"
            )
        }
    }

    Text(
        text = texto,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab02CarritoPOOTheme {
        PruebaPOO()
    }
}