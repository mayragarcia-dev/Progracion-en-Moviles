package com.tecsup.mibodega.ui.cliente.modelo

import com.tecsup.mibodega.R

/**
 * Datos de ejemplo (fake) para mostrar la UI sin base de datos.
 * Cuando conecten Room o una API, este archivo se reemplaza por
 * un Repository real, pero las pantallas no cambian porque ya
 * reciben una List<Producto> como parámetro.
 */
val listaCategorias = listOf("Todos", "Bebidas", "Abarrotes", "Snacks")

val listaProductosFake = listOf(
    Producto(
        id = 1,
        nombre = "Arroz Costeño",
        descripcion = "Arroz extra, grano largo, ideal para el día a día.",
        precio = 4.50,
        categoria = "Abarrotes",
        imagenRes = R.drawable.arroz
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        descripcion = "Aceite vegetal 1 L, alto en vitamina E.",
        precio = 8.90,
        categoria = "Abarrotes",
        imagenRes = R.drawable.aceite
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        descripcion = "Leche evaporada entera 1 L.",
        precio = 5.20,
        categoria = "Abarrotes",
        imagenRes = R.drawable.leche
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        descripcion = "Galletas de chocolate rellenas 126 g.",
        precio = 3.50,
        categoria = "Snacks",
        imagenRes = R.drawable.galleta
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        imagenRes = R.drawable.cocacola
    ),
    Producto(
        id = 6,
        nombre = "Inka Kola",
        descripcion = "Bebida gaseosa sabor nacional 1.5 L.",
        precio = 6.50,
        categoria = "Bebidas",
        imagenRes = R.drawable.inka
    ),
    Producto(
        id = 7,
        nombre = "Atún Florida",
        descripcion = "Lomito de atún en aceite vegetal 140 g.",
        precio = 5.50,
        categoria = "Abarrotes",
        imagenRes = R.drawable.atun
    ),
    Producto(
        id = 8,
        nombre = "Azúcar Rubia Cartavio",
        descripcion = "Azúcar rubia de caña selección especial.",
        precio = 4.80,
        categoria = "Abarrotes"
    ),
    Producto(
        id = 9,
        nombre = "Papitas Lays",
        descripcion = "Papas fritas sabor clásico 140 g.",
        precio = 6.20,
        categoria = "Snacks",
        imagenRes = R.drawable.papitas_lays
    ),
    Producto(
        id = 10,
        nombre = "Detergente Bolívar",
        descripcion = "Detergente en polvo aroma bebé 1 kg.",
        precio = 9.50,
        categoria = "Abarrotes"
    )
)
