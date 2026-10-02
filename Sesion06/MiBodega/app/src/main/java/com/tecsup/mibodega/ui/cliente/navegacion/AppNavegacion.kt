package com.tecsup.mibodega.ui.cliente.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen

@Composable
fun AppNavegacion(
    navController: NavHostController
) {
    var carrito by remember {
        mutableStateOf<List<ItemCarrito>>(emptyList())
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {

        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = {
                    navController.navigate(Rutas.REGISTRO)
                },
                onIniciarSesion = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) {
                            inclusive = true
                        }
                    }
                },
                onTerminos = {
                }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = {
                    navController.popBackStack()
                },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                onVerCarrito = {
                    navController.navigate(Rutas.CARRITO)
                },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(
                        carrito,
                        producto,
                        1
                    )
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(
                navArgument("productoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val productoId =
                backStackEntry.arguments?.getInt("productoId") ?: 0

            val producto =
                listaProductosFake.first { it.id == productoId }

            DetalleProductoScreen(
                producto = producto,
                onVolver = {
                    navController.popBackStack()
                },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(
                        carrito,
                        productoSeleccionado,
                        cantidad
                    )
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = {
                    navController.popBackStack()
                },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) {
                            it.copy(cantidad = it.cantidad + 1)
                        } else {
                            it
                        }
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 ->
                                it.copy(cantidad = it.cantidad - 1)
                            else -> null
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot {
                        it.producto.id == producto.id
                    }
                },
                onContinuarPedido = {
                    navController.navigate(Rutas.DATOS_ENTREGA)
                }
            )
        }

        composable(Rutas.DATOS_ENTREGA) {
            DatosEntregaScreen(
                onVolver = {
                    navController.popBackStack()
                },
                onConfirmarPedido = { direccion, referencia, telefono, metodoPago ->
                    // Vaciar carrito opcionalmente o proceder a confirmación con popUpTo
                    carrito = emptyList()
                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.INICIO) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                onIrAInicio = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {

    val itemExistente =
        carrito.find { it.producto.id == producto.id }

    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) {
                it.copy(cantidad = it.cantidad + cantidad)
            } else {
                it
            }
        }
    } else {
        carrito + ItemCarrito(
            producto = producto,
            cantidad = cantidad
        )
    }
}
