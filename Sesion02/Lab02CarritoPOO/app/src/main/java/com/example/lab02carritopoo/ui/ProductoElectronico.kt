package com.example.lab02carritopoo

class ProductoElectronico(
    nombre: String,
    precio: Double,
    cantidad: Int,
    private val marca: String
) : Producto(nombre, precio, cantidad) {

    fun getMarca(): String {
        return marca
    }

    override fun mostrarTipo(): String {
        return "Producto electrónico - Marca: $marca"
    }
}