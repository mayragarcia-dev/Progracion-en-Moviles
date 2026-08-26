package com.example.lab02carritopoo

class Carrito {

    private val productos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    fun obtenerProductos(): List<Producto> {
        return productos.toList()
    }

    fun calcularSubtotal(): Double {
        var subtotal = 0.0

        for (producto in productos) {
            subtotal += producto.getPrecio() * producto.getCantidad()
        }

        return subtotal
    }

    fun calcularIGV(): Double {
        return calcularSubtotal() * 0.18
    }

    fun calcularTotal(): Double {
        return calcularSubtotal() + calcularIGV()
    }
    fun obtenerProductoMasCaro(): Producto? {
        return productos.maxByOrNull { it.getPrecio() }
    }
    fun calcularDescuento(): Double {
        val total = calcularTotal()

        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }
    fun calcularTotalConDescuento(): Double {
        return calcularTotal() - calcularDescuento()
    }
}