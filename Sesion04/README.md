# Laboratorio 04 - Carrito de Compras TECSUP

**Nombre:** Mayra Julisa García Rojas

## Descripción

Este proyecto consiste en una aplicación de carrito de compras desarrollada con Kotlin y Jetpack Compose.

La aplicación permite ingresar productos indicando su nombre, precio y cantidad. Los productos se muestran en una lista y se puede eliminar cada producto.

También se muestra un resumen de compra con la cantidad de productos, subtotal, IGV del 18% y total.

## Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Android Studio
* Material 3

## Funcionalidades

* Registrar productos.
* Mostrar productos en una lista.
* Mostrar nombre, precio, cantidad e importe.
* Eliminar productos.
* Mostrar el estado vacío cuando no hay productos.
* Calcular automáticamente subtotal, IGV y total.
* Mostrar la cantidad de productos agregados.

## Conceptos aprendidos

### 1. ¿Cuál es la ventaja de usar LazyColumn en lugar de Column?

`LazyColumn` permite mostrar una lista de elementos de forma más eficiente, porque solo carga los elementos que son necesarios en pantalla. Es útil cuando tenemos muchos productos.

### 2. ¿Qué diferencia hay entre mutableListOf y mutableStateListOf?

`mutableListOf` permite crear una lista modificable, pero los cambios no actualizan automáticamente la interfaz de Compose.

`mutableStateListOf` permite modificar la lista y hace que Compose detecte los cambios para actualizar la pantalla.

### 3. ¿Cómo funciona el botón eliminar?

El botón eliminar ejecuta la función `onEliminar`. Esta función elimina el producto seleccionado de la lista `productos`.

Al cambiar la lista, Compose actualiza la interfaz y el producto eliminado deja de mostrarse.

### 4. ¿Por qué los totales se recalculan automáticamente?

Los totales dependen de la lista `productos`. Cuando se agrega o elimina un producto, la lista cambia y Compose vuelve a calcular el subtotal, el IGV y el total.

### 5. ¿Para qué sirve Arrangement.SpaceBetween?

`Arrangement.SpaceBetween` permite colocar un elemento al inicio de la fila y otro al final, dejando el espacio disponible entre ellos.

En este proyecto se utiliza para separar los nombres de los valores del resumen de compra.

## Capturas de pantalla
<img width="297" height="614" alt="Captura de pantalla 2026-09-16 a las 5 41 35 p  m" src="https://github.com/user-attachments/assets/5d523a65-8ce0-4276-84a0-84b9f210cfd0" />


### Carrito con productos

<img width="293" height="631" alt="Captura de pantalla 2026-09-16 a las 5 42 21 p  m" src="https://github.com/user-attachments/assets/8fbf3c20-d0cc-4e18-8462-a41e08e817d7" />

### Resumen de compra

Aquí se colocará una captura mostrando el subtotal, IGV, total y cantidad de productos.

