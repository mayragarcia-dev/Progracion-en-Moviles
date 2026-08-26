# Laboratorio 02 - Carrito de Compras en Kotlin

**Nombre:** Mayra Julisa Garcia Rojas

## Descripción

Este proyecto consiste en desarrollar un programa de carrito de compras utilizando el lenguaje Kotlin.

El programa permite registrar productos indicando su nombre, precio y cantidad. También permite mostrar los productos agregados al carrito y calcular el subtotal, el IGV del 18% y el total de la compra.

Además, el programa identifica el producto más caro y aplica un descuento dependiendo del monto total de la compra.

## Funciones implementadas

Las principales funciones implementadas en el programa son:

* `calcularSubtotal()` - Calcula el subtotal de todos los productos considerando su precio y cantidad.
* `calcularIGV()` - Calcula el IGV correspondiente al 18% del subtotal.
* `calcularTotal()` - Calcula el total sumando el subtotal y el IGV.
* `mostrarDetalle()` - Muestra el detalle de los productos del carrito con cantidades e importes alineados.
* `calcularDescuento()` - Calcula el descuento según el monto total utilizando la estructura `when`.
* `maxByOrNull` - Permite encontrar el producto con el precio más alto.

## Productos registrados

El carrito contiene los siguientes productos:

* Laptop HP
* Mouse Logitech
* Teclado Mecánico
* Audífonos Sony

## Resultado de la ejecución

El programa muestra el detalle del carrito y realiza los cálculos correspondientes.

captura-consola.png

### Resultado final

* **Subtotal:** S/ 3731.00
* **IGV (18%):** S/ 671.58
* **Total a pagar:** S/ 4402.58
* **Producto más caro:** Laptop HP (S/ 2500.00)
* **Descuento aplicado:** S/ 220.13
* **Total con descuento:** S/ 4182.45

## Respuesta: ¿Por qué `val` y `var`?

En Kotlin, `val` se utiliza para declarar una variable cuyo valor no puede ser reasignado después de su inicialización. En cambio, `var` permite modificar el valor de una variable posteriormente.

En la clase `Producto`, `nombre` y `precio` fueron declarados como `val` porque representan datos que no deberían cambiar después de crear el producto.

Por otro lado, `cantidad` fue declarada como `var` porque la cantidad de un producto puede cambiar durante el uso del carrito.

Si se intenta cambiar el precio después de crear un producto, Kotlin mostrará un error porque `precio` fue declarado como `val`.

## Conclusión

En este laboratorio se aplicaron conceptos fundamentales de Kotlin, como `data class`, variables `val` y `var`, listas mutables, funciones, ciclos `for`, estructuras `when`, funciones de colecciones como `maxByOrNull` y formato de salida mediante `String.format`.

## Captura
<img width="1903" height="978" alt="captura-consola png" src="https://github.com/user-attachments/assets/ff7876e7-7a58-4a3a-8314-4fc0df219583" />


