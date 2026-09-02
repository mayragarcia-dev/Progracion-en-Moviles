# Laboratorio 03 - Registro de Producto

## Datos del estudiante

**Nombre:** Mayra Garcia Rojas
**Curso:** Programación en Móviles
**Laboratorio:** Laboratorio 03 - Diseño de interfaces con Jetpack Compose

## Descripción

Aplicación Android desarrollada con Kotlin y Jetpack Compose para registrar productos.

La pantalla permite ingresar el nombre del producto, precio y cantidad. Al presionar el botón **AGREGAR PRODUCTO**, se muestra una tarjeta con el resumen del producto y el importe total calculado.

## Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Android Studio

## Capturas de pantalla

### Pantalla de registro
<img width="1189" height="755" alt="Captura de pantalla 2026-09-02 a las 5 45 11 p  m" src="https://github.com/user-attachments/assets/6ae1371f-5760-4e84-82c6-daca9fa0ca2c" />
### Resumen del producto
<img width="1194" height="749" alt="Captura de pantalla 2026-09-02 a las 5 46 29 p  m" src="https://github.com/user-attachments/assets/21c56f60-2689-4346-98fc-a31b14671dfe" />

## Funcionalidades

* Ingreso del nombre del producto.
* Ingreso del precio.
* Ingreso de la cantidad.
* Cálculo automático del importe.
* Visualización del resumen mediante una `Card`.
* Mensaje de confirmación del registro.
* Uso de estados con `remember` y `mutableStateOf`.

## ¿Qué pasaría si declaras las variables de los campos SIN remember?

Si las variables de los campos se declararan sin `remember`, su valor no se conservaría cuando Compose vuelva a ejecutar la función composable durante una recomposición.

Por ejemplo, si el usuario escribe un nombre en el `TextField` y la variable no utiliza `remember`, el valor podría volver a su valor inicial y el texto ingresado se perdería.

Por eso utilizamos:

```kotlin
var nombreProducto by remember {
    mutableStateOf("")
}
```

`remember` permite que Compose conserve el valor del estado durante las recomposiciones. De esta manera, cuando el usuario escribe en un `TextField`, el valor permanece visible y la interfaz puede actualizarse correctamente.

## Cálculo del importe

Para convertir los valores ingresados se utilizaron:

```kotlin
val precioNumero = precio.toDoubleOrNull() ?: 0.0
val cantidadNumero = cantidad.toIntOrNull() ?: 0
```

Luego se calcula:

```kotlin
val importe = precioNumero * cantidadNumero
```

El importe se muestra con dos decimales:

```kotlin
String.format("%.2f", importe)
```

## Historial de commits

El desarrollo fue realizado progresivamente mediante commits en la rama `main`, registrando cada avance del laboratorio.

* Creación del proyecto.
* Agregado del encabezado.
* Agregado de campos de ingreso con estado.
* Agregado del botón y resumen.
* Aplicación de reglas de diseño y mensaje de confirmación.
* Mejora visual de la interfaz.
* Documentación del proyecto mediante este README.

