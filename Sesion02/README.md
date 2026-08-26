# Laboratorio 02 - Carrito de Compras en Kotlin con POO

**Nombre:** Mayra Julisa Garcia Rojas

## Descripción

Este proyecto consiste en desarrollar un programa de carrito de compras utilizando el lenguaje Kotlin y aplicando conceptos fundamentales de Programación Orientada a Objetos (POO).

El programa permite registrar productos indicando su nombre, precio y cantidad. Además, se implementa una clase especializada para productos electrónicos, permitiendo aplicar conceptos de encapsulamiento, herencia y polimorfismo.

El carrito permite mostrar los productos agregados, calcular el subtotal, el IGV del 18%, el total de la compra, aplicar descuentos según el monto total y determinar cuál es el producto más caro.

## Conceptos de POO implementados

En este laboratorio se aplicaron los siguientes conceptos:

* **Encapsulamiento:** Los atributos de las clases se mantienen privados y se accede a ellos mediante métodos como `getNombre()`, `getPrecio()` y `getCantidad()`.
* **Herencia:** La clase `ProductoElectronico` hereda de la clase `Producto`.
* **Polimorfismo:** Se utiliza una referencia de tipo `Producto` para almacenar objetos de diferentes tipos y cada producto puede implementar su propio comportamiento mediante `mostrarTipo()`.
* **Clases:** Se utilizaron las clases `Producto`, `ProductoElectronico` y `Carrito`.
* **Colecciones:** Se utilizó una lista mutable para almacenar los productos del carrito.

## Clases implementadas

### Producto

La clase `Producto` representa un producto general y contiene los siguientes datos:

* Nombre
* Precio
* Cantidad

También incluye métodos para obtener la información del producto y mostrar su tipo.

### ProductoElectronico

La clase `ProductoElectronico` hereda de `Producto` y agrega la información de la marca del producto.

Ejemplos registrados:

* Laptop HP
* Laptop Lenovo

La clase también sobrescribe el comportamiento de `mostrarTipo()` para mostrar que se trata de un producto electrónico y su marca.

### Carrito

La clase `Carrito` administra los productos registrados y contiene las siguientes funciones:

* `agregarProducto()` - Agrega un producto al carrito.
* `obtenerProductos()` - Devuelve la lista de productos registrados.
* `calcularSubtotal()` - Calcula el subtotal considerando precio y cantidad.
* `calcularIGV()` - Calcula el IGV correspondiente al 18%.
* `calcularTotal()` - Calcula el total sumando subtotal e IGV.
* `obtenerProductoMasCaro()` - Identifica el producto con el precio más alto utilizando `maxByOrNull`.
* `calcularDescuento()` - Calcula el descuento según el monto total utilizando `when`.
* `calcularTotalConDescuento()` - Calcula el total final después de aplicar el descuento.

## Productos registrados

Para probar el funcionamiento del carrito se registraron los siguientes productos:

* Producto general - S/ 100.00
* Laptop HP - S/ 2500.00
* Laptop Lenovo - S/ 3500.00

## Resultado de la ejecución

La aplicación fue compilada correctamente y probada en un dispositivo Android físico.

El resultado mostrado por la aplicación es:

```text
===== CARRITO DE COMPRAS =====

Producto general - S/ 100.00 x 1
Producto general

Laptop HP - S/ 2500.00 x 1
Producto electrónico - Marca: HP

Laptop Lenovo - S/ 3500.00 x 1
Producto electrónico - Marca: Lenovo

------------------------------
Subtotal: S/ 6100.00
IGV (18%): S/ 1098.00
Total: S/ 7198.00
Descuento: S/ 719.80
Total final: S/ 6478.20

Producto más caro: Laptop Lenovo - S/ 3500.00
```

### Resultado final

* **Subtotal:** S/ 6100.00
* **IGV (18%):** S/ 1098.00
* **Total:** S/ 7198.00
* **Descuento:** S/ 719.80
* **Total final:** S/ 6478.20
* **Producto más caro:** Laptop Lenovo - S/ 3500.00

## Aplicación del descuento

El descuento se calcula según el total de la compra utilizando la estructura `when`:

* Si el total es mayor a S/ 5000, se aplica un **10% de descuento**.
* Si el total es mayor a S/ 3000, se aplica un **5% de descuento**.
* En cualquier otro caso, no se aplica descuento.

En este caso, el total es S/ 7198.00, por lo que corresponde un descuento del 10%:

**S/ 7198.00 × 10% = S/ 719.80**

Por lo tanto:

**S/ 7198.00 - S/ 719.80 = S/ 6478.20**

## Respuesta: ¿Cómo se aplicó el encapsulamiento, herencia y polimorfismo?

El **encapsulamiento** se aplicó utilizando atributos privados dentro de las clases y métodos para acceder a la información de los productos.

La **herencia** se aplicó mediante la clase `ProductoElectronico`, que hereda de `Producto` y reutiliza sus características principales.

El **polimorfismo** se aplicó mediante `mostrarTipo()`. El carrito trabaja con objetos de tipo `Producto`, pero cuando recibe un `ProductoElectronico`, se ejecuta la implementación correspondiente a ese tipo de producto.

Esto permite que diferentes tipos de productos puedan almacenarse en el mismo carrito y comportarse de acuerdo con su clase.

## Conclusión

En este laboratorio se aplicaron conceptos fundamentales de Programación Orientada a Objetos en Kotlin, como encapsulamiento, herencia y polimorfismo.

También se trabajó con clases, listas mutables, funciones, ciclos `for`, estructuras `when` y funciones de colecciones como `maxByOrNull`.

Finalmente, se implementó un carrito de compras funcional que permite registrar productos, calcular subtotal, IGV, total, descuentos y determinar el producto más caro. El proyecto fue compilado y probado correctamente en un dispositivo Android.

## Captura de los promnt 
<img width="952" height="891" alt="Captura-promnt1" src="https://github.com/user-attachments/assets/d5ab3783-db9f-4161-913a-39ed45f05b6b" />
<img width="971" height="941" alt="Captura-promnt" src="https://github.com/user-attachments/assets/326b98da-0b06-407a-b671-7a85abb54f47" />



