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
## Mejora con IA

Para esta mejora utilicé inteligencia artificial como herramienta de apoyo para implementar nuevas funcionalidades en el formulario de registro de productos. La propuesta generada fue revisada, adaptada y probada antes de incorporarla a la rama `mejora-ia`.

### Prompts utilizados y decisiones tomadas

| Prompt que usé                                                                                                                                                                                                                                                                                                       | Qué generó Gemini                                                                                                                                                                                                           | Qué acepté o corregí y por qué                                                                                                                                                                      |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **"Agrega a este formulario de registro de producto una validación para evitar que se agreguen productos cuando el nombre esté vacío, el precio sea inválido o menor o igual a 0, o la cantidad sea inválida o menor o igual a 0. También agrega un botón LIMPIAR que borre todos los campos y oculte el resumen."** | Generó una validación mediante `when`, utilizando `toDoubleOrNull()` y `toIntOrNull()`. También agregó el estado `mensajeError` para mostrar errores y un botón `LIMPIAR` para restablecer los campos y ocultar el resumen. | **Acepté** la propuesta porque cumplía con la mejora solicitada. Después revisé el código y comprobé mediante pruebas que la validación y el botón `LIMPIAR` funcionaran correctamente.             |
| **"Revisa la validación del precio para que también permita ingresar valores decimales usando coma, por ejemplo 5,50, además del punto decimal."**                                                                                                                                                                   | Propuso utilizar `replace(",", ".").toDoubleOrNull()` para convertir la coma decimal en punto antes de realizar la conversión a `Double`.                                                                                   | **Corregí y adapté** el código para aplicar esta conversión en la validación del precio. Esto permite ingresar valores como `5,50`, una forma habitual de representar decimales en español.         |
| **"Verifica que la corrección para aceptar coma decimal también se aplique al cálculo del importe y no solamente a la validación."**                                                                                                                                                                                 | Indicó que la conversión mediante `replace(",", ".")` también debía utilizarse al obtener el precio para calcular el importe.                                                                                               | **Acepté la corrección** y la incorporé también en el cálculo. Finalmente, realicé una prueba ingresando `5,50` como precio y `2` como cantidad, obteniendo correctamente un importe de `S/ 11.00`. |

### Decisiones tomadas

La inteligencia artificial se utilizó como **herramienta de apoyo**, pero las propuestas generadas no fueron incorporadas automáticamente. El código fue revisado, adaptado y probado para verificar que cumpliera con los requisitos del laboratorio y funcionara correctamente.

La mejora final incorpora las siguientes funcionalidades:

* Validación del nombre del producto para evitar campos vacíos.
* Validación del precio para comprobar que sea numérico y mayor que `0`.
* Validación de la cantidad para comprobar que sea numérica y mayor que `0`.
* Mensajes de error cuando los datos ingresados no son válidos.
* Botón `LIMPIAR` para borrar todos los datos ingresados.
* Ocultamiento del resumen cuando los datos son inválidos o se limpia el formulario.
* Compatibilidad con precios ingresados utilizando punto o coma decimal.
* Cálculo correcto del importe mediante precio × cantidad.
* Prueba funcional con un precio de `5,50` y una cantidad de `2`, obteniendo `S/ 11.00`.

### Resultado de la mejora

La incorporación de estas funcionalidades permitió mejorar la experiencia de usuario, evitando registros con datos incorrectos y facilitando el ingreso de precios decimales según el formato utilizado habitualmente en español.

<img width="1600" height="1600" alt="image" src="https://github.com/user-attachments/assets/89f41a88-343f-443b-abc5-06f88a42b6f7" />

<img width="1859" height="1017" alt="image" src="https://github.com/user-attachments/assets/75b5ce88-2001-4cf7-926b-8a7e6c5f4270" />





