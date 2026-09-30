# Lab 04: Carrito de Compras TECSUP

**Nombre:** Mayra Garcia
**Descripción:** Aplicación de carrito de compras desarrollada en Jetpack Compose que permite agregar productos, visualizarlos en una lista, eliminarlos con confirmación y calcular subtotales, IGV y descuentos automáticos.

## Capturas de Pantalla
<img width="1538" height="1599" alt="image" src="https://github.com/user-attachments/assets/f2924647-34da-41ac-baf4-746d3fdcc9c9" />


## Respuestas Conceptuales

### (a) ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Una `MutableList` normal no es observable por Compose. Si agregas o eliminas elementos de una `MutableList` estándar, Compose no detectará el cambio y no recompondrá la UI (la lista en pantalla no se actualizará). `mutableStateListOf` crea una lista especial que notifica a Compose cada vez que su contenido cambia, provocando la actualización automática de la interfaz.

### (b) ¿Por qué la lista se declara con `val`?
Se declara con `val` porque la **referencia** a la lista no cambia (siempre es el mismo objeto lista creado por `remember`). Lo que cambia es el **contenido** interno de la lista (sus elementos). En Kotlin, `val` impide reasignar la variable a una nueva lista, pero no impide modificar el contenido de la estructura de datos mutable a la que apunta.

### (c) ¿Qué hace `weight(1f)` en la `LazyColumn`?
El modificador `weight(1f)` dentro de una `Column` indica que el componente debe ocupar todo el espacio vertical sobrante. En este caso, permite que la `LazyColumn` se expanda para llenar el centro de la pantalla, empujando el panel de totales hacia la parte inferior y manteniéndolo siempre visible (fijo abajo) independientemente de cuántos productos haya.

## Funcionalidades Implementadas
- [x] Modelo de datos `Producto`.
- [x] Formulario compacto (Nombre, Precio/Cant en fila).
- [x] Lista observable con `LazyColumn`.
- [x] Tarjeta de producto con diseño según guía.
- [x] Eliminación de productos con elevación de eventos (`onEliminar`).
- [x] Panel de totales con Subtotal e IGV (18%).
- [x] **Reto:** Confirmación de borrado mediante `AlertDialog`.
- [x] **Reto:** Sistema de descuentos dinámicos (5% > 3000, 10% > 5000).
