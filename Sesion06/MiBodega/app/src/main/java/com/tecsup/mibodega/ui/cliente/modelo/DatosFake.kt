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
        imagenRes = R.drawable.ic_arroz,
        imagenUrl = "https://plazavea.vteximg.com.br/arquivos/ids/27552446-418-418/433778.jpg"
    ),
    Producto(
        id = 2,
        nombre = "Aceite Primor",
        descripcion = "Aceite vegetal 1 L, alto en vitamina E.",
        precio = 8.90,
        categoria = "Abarrotes",
        imagenRes = R.drawable.ic_aceite,
        imagenUrl = "https://media.falabella.com/tottusPE/42757359_2/w=1500,h=1500,fit=cover"
    ),
    Producto(
        id = 3,
        nombre = "Leche Gloria",
        descripcion = "Leche evaporada entera 1 L.",
        precio = 5.20,
        categoria = "Abarrotes",
        imagenRes = R.drawable.ic_leche,
        imagenUrl = "https://corporacionliderperu.com/50720-large_default/gloria-leche-tarro-azul-gde-x-390-gr.jpg"
    ),
    Producto(
        id = 4,
        nombre = "Galleta Oreo",
        descripcion = "Galletas de chocolate rellenas 126 g.",
        precio = 3.50,
        categoria = "Snacks",
        imagenRes = R.drawable.ic_galleta,
        imagenUrl = "https://media.falabella.com/tottusPE/43331113_6/w=1500,h=1500,fit=cover"
    ),
    Producto(
        id = 5,
        nombre = "Coca-Cola Original",
        descripcion = "Bebida gaseosa sabor cola. Ideal para compartir en familia.",
        precio = 6.50,
        categoria = "Bebidas",
        imagenRes = R.drawable.ic_cocacola,
        imagenUrl = "https://tiptop.com.pe/wp-content/uploads/2025/12/1.5-CC-Original-1.5-L.webp"
    ),
    Producto(
        id = 6,
        nombre = "Inka Kola",
        descripcion = "Bebida gaseosa sabor nacional 1.5 L.",
        precio = 6.50,
        categoria = "Bebidas",
        imagenUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSB4DLsrnP6LedEb0XVnp6pl7s711rz5y0YqMmbv4L5PtlkOr0jp9rIUk&s=10"
    ),
    Producto(
        id = 7,
        nombre = "Atún Florida",
        descripcion = "Lomito de atún en aceite vegetal 140 g.",
        precio = 5.50,
        categoria = "Abarrotes",
        imagenUrl = "https://corporacionliderperu.com/53270-large_default/florida-filete-de-atun-x-140-gr.jpg"
    ),
    Producto(
        id = 8,
        nombre = "Azúcar Rubia Cartavio",
        descripcion = "Azúcar rubia de caña selección especial.",
        precio = 4.80,
        categoria = "Abarrotes",
        imagenUrl = "https://plazavea.vteximg.com.br/arquivos/ids/28650077-450-450/20184796.jpg?v=638411617015600000"
    ),
    Producto(
        id = 9,
        nombre = "Papitas Lays",
        descripcion = "Papas fritas sabor clásico 140 g.",
        precio = 6.20,
        categoria = "Snacks",
        imagenUrl = "https://vegaperu.vtexassets.com/arquivos/ids/166006/140571.jpg?v=638428480740100000"
    ),
    Producto(
        id = 10,
        nombre = "Detergente Bolívar",
        descripcion = "Detergente en polvo aroma bebé 1 kg.",
        precio = 9.50,
        categoria = "Abarrotes",
        imagenUrl = "https://promart.vteximg.com.br/arquivos/ids/8725249/130955.jpg?v=639214524475600000"
    )
)
