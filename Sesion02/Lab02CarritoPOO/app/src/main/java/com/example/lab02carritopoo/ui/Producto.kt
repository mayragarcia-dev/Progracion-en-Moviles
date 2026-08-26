package com.example.lab02carritopoo

open class Producto(
    private val nombre: String,
    private val precio: Double,
    private var cantidad: Int
) {

    fun getNombre(): String {
        return nombre
    }

    fun getPrecio(): Double {
        return precio
    }

    fun getCantidad(): Int {
        return cantidad
    }

    fun cambiarCantidad(nuevaCantidad: Int) {
        if (nuevaCantidad > 0) {
            cantidad = nuevaCantidad
        }
    }

    open fun mostrarTipo(): String {
        return "Producto general"
    }
}