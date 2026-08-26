import java.util.Scanner

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

data class Cliente(
    val nombre: String,
    val carrito: MutableList<Producto>
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0

    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }

    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {

    println("--------- DETALLE DEL CARRITO ---------")

    var i = 1

    for (p in productos) {

        val importe = p.precio * p.cantidad

        println(
            String.format(
                "%d. %-20s x%d S/ %8.2f",
                i,
                p.nombre,
                p.cantidad,
                importe
            )
        )

        i++
    }

    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun mostrarTotal(productos: List<Producto>) {

    val subtotal = calcularSubtotal(productos)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    mostrarDetalle(productos)

    println()

    println(
        String.format(
            "%-20s S/ %8.2f",
            "Subtotal:",
            subtotal
        )
    )

    println(
        String.format(
            "%-20s S/ %8.2f",
            "IGV (18%):",
            igv
        )
    )

    println(
        String.format(
            "%-20s S/ %8.2f",
            "TOTAL A PAGAR:",
            total
        )
    )

    val masCaro = productos.maxByOrNull { it.precio }

    if (masCaro != null) {

        println(
            "Producto mas caro: ${masCaro.nombre} " +
                    String.format(
                        "(S/ %.2f)",
                        masCaro.precio
                    )
        )
    }

    val descuento = calcularDescuento(total)

    if (descuento > 0) {

        println(
            String.format(
                "Descuento aplicado: S/ %.2f",
                descuento
            )
        )

    } else {

        println("Descuento aplicado: S/ 0.00")
    }

    val totalConDescuento = total - descuento

    println(
        String.format(
            "%-20s S/ %8.2f",
            "TOTAL CON DESCUENTO:",
            totalConDescuento
        )
    )
}

fun main() {

    val scanner = Scanner(System.`in`)

    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    // =========================================
    // CLIENTE INICIAL
    // =========================================

    val clientes = mutableListOf<Cliente>()

    val carritoMayra = mutableListOf<Producto>()

    // Productos que ya tenías
    carritoMayra.add(
        Producto("Laptop HP", 2500.0, 1)
    )

    carritoMayra.add(
        Producto("Mouse Logitech", 45.5, 2)
    )

    carritoMayra.add(
        Producto("Teclado Mecánico", 180.0, 1)
    )

    carritoMayra.add(
        Producto("Audífonos Sony", 320.0, 3)
    )

    clientes.add(
        Cliente(
            "Mayra Garcia",
            carritoMayra
        )
    )

    var clienteActual = clientes[0]

    println()
    println("Cliente actual: ${clienteActual.nombre}")

    // =========================================
    // MENÚ
    // =========================================

    var opcion: Int

    do {

        println()
        println("=========================================")
        println("              MENÚ PRINCIPAL")
        println("=========================================")
        println("1. Agregar cliente")
        println("2. Seleccionar cliente")
        println("3. Agregar producto")
        println("4. Ver carrito")
        println("5. Calcular total")
        println("6. Ver clientes")
        println("7. Salir")
        println("=========================================")
        print("Seleccione una opción: ")

        opcion = scanner.nextInt()
        scanner.nextLine()

        when (opcion) {

            // =================================
            // AGREGAR CLIENTE
            // =================================

            1 -> {

                println()
                println("--------- AGREGAR CLIENTE ---------")

                print("Ingrese nombre del cliente: ")
                val nombre = scanner.nextLine()

                if (nombre.isNotBlank()) {

                    val nuevoCliente = Cliente(
                        nombre,
                        mutableListOf()
                    )

                    clientes.add(nuevoCliente)

                    println()
                    println("Cliente agregado correctamente.")

                } else {

                    println()
                    println("El nombre no puede estar vacío.")
                }
            }

            // =================================
            // SELECCIONAR CLIENTE
            // =================================

            2 -> {

                println()
                println("--------- CLIENTES ---------")

                for (i in clientes.indices) {

                    println(
                        "${i + 1}. ${clientes[i].nombre}"
                    )
                }

                print("Seleccione un cliente: ")

                val numeroCliente = scanner.nextInt()
                scanner.nextLine()

                if (
                    numeroCliente >= 1 &&
                    numeroCliente <= clientes.size
                ) {

                    clienteActual =
                        clientes[numeroCliente - 1]

                    println()
                    println(
                        "Cliente seleccionado: " +
                                clienteActual.nombre
                    )

                } else {

                    println()
                    println("Cliente inválido.")
                }
            }

            // =================================
            // AGREGAR PRODUCTO
            // =================================

            3 -> {

                println()
                println("--------- AGREGAR PRODUCTO ---------")

                print("Ingrese nombre del producto: ")
                val nombre = scanner.nextLine()

                print("Ingrese precio: ")
                val precio = scanner.nextDouble()

                print("Ingrese cantidad: ")
                val cantidad = scanner.nextInt()

                scanner.nextLine()

                if (
                    nombre.isNotBlank() &&
                    precio > 0 &&
                    cantidad > 0
                ) {

                    val nuevoProducto = Producto(
                        nombre,
                        precio,
                        cantidad
                    )

                    clienteActual.carrito.add(
                        nuevoProducto
                    )

                    println()
                    println(
                        "Producto agregado correctamente."
                    )

                } else {

                    println()
                    println(
                        "Datos inválidos. " +
                                "No se agregó el producto."
                    )
                }
            }

            // =================================
            // VER CARRITO
            // =================================

            4 -> {

                println()
                println(
                    "Cliente: ${clienteActual.nombre}"
                )

                println()

                if (clienteActual.carrito.isEmpty()) {

                    println("El carrito está vacío.")

                } else {

                    mostrarDetalle(
                        clienteActual.carrito
                    )
                }
            }

            // =================================
            // CALCULAR TOTAL
            // =================================

            5 -> {

                println()
                println(
                    "Cliente: ${clienteActual.nombre}"
                )

                if (clienteActual.carrito.isEmpty()) {

                    println("El carrito está vacío.")

                } else {

                    println()

                    mostrarTotal(
                        clienteActual.carrito
                    )
                }
            }

            // =================================
            // VER CLIENTES
            // =================================

            6 -> {

                println()
                println("--------- CLIENTES REGISTRADOS ---------")

                for (i in clientes.indices) {

                    println(
                        "${i + 1}. " +
                                clientes[i].nombre +
                                " - " +
                                "${clientes[i].carrito.size} productos"
                    )
                }
            }

            // =================================
            // SALIR
            // =================================

            7 -> {

                println()
                println("=========================================")
                println("Gracias por usar el sistema.")
                println("Programa finalizado.")
                println("=========================================")
            }

            else -> {

                println()
                println(
                    "Opción inválida."
                )
            }
        }

    } while (opcion != 7)

    scanner.close()
}