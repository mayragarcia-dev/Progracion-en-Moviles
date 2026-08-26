data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Mayra Garcia"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente")
    println()
}