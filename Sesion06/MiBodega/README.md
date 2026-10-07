# Mi Bodega - Aplicación Android con Jetpack Compose

<img width="875" height="665" alt="image" src="https://github.com/user-attachments/assets/84bab43c-15dc-4143-9c9a-09586bab1b9d" />
<img width="925" height="636" alt="image" src="https://github.com/user-attachments/assets/06009af8-fed8-4d89-9110-f824e0bebaf3" />
<img width="565" height="573" alt="image" src="https://github.com/user-attachments/assets/d58bcffe-48cf-4bde-8d40-e0f7d4d1b598" />



## VI. Preguntas de reflexión

### 1. ¿Por qué `Producto.kt` y `MainActivity.kt` se entregaron completos, y las pantallas no? ¿Qué tienen en común los archivos que sí se dejaron como esqueleto?
* **Respuesta:**
  `Producto.kt` es una clase de modelo de datos (`data class`) simple y `MainActivity.kt` es el punto de entrada/contenedor básico de la actividad Android. En cambio, las pantallas requerían ser desarrolladas por el estudiante para poner en práctica la gestión de estado (`remember`, `mutableStateOf`), la maquetación declarativa en Jetpack Compose, el flujo de eventos con callbacks y la navegación con `NavHost`.
  Los archivos que se dejaron como esqueleto tienen en común que forman parte de la capa de interfaz de usuario (UI y navegación), los cuales dependen directamente de la lógica de presentación del flujo de la aplicación cliente.

---

### 2. ¿Cómo lograste que el filtro de categoría (`LazyRow`) y el cálculo del carrito reaccionen automáticamente sin que tú "actualices" nada a mano?
* **Respuesta:**
  Esto se logró mediante el paradigma **declarativo y reactivo** de Jetpack Compose. Al declarar las variables de estado con `remember { mutableStateOf(...) }` (como `categoriaSeleccionada` o el estado `carrito`), Compose escucha los cambios en dichas fuentes de verdad. Cuando el valor cambia, Jetpack Compose ejecuta automáticamente un proceso de **recomposición** (*recomposition*), volviendo a renderizar únicamente los componentes de la interfaz cuyos datos hayan variado (como la lista filtrada de productos o el cálculo del total en el carrito) sin necesidad de manipular las vistas manualmente como se hacía tradicionalmente en XML.

---

### 3. ¿Qué diferencia notaste entre `navigate()` normal (Inicio → Detalle) y el que usa `popUpTo` (Datos de entrega → Confirmación)?
* **Respuesta:**
  * **`navigate()` normal (Inicio → Detalle):** Agrega la nueva pantalla en la parte superior de la pila de navegación (*backstack*). Al presionar el botón "Atrás" de Android o el ícono de retroceso, el usuario vuelve a la pantalla anterior manteniendo el estado intacto.
  * **`navigate()` con `popUpTo` (Datos de entrega → Confirmación):** Remueve de la pila de navegación las pantallas intermedias especificadas (como `CARRITO` y `DATOS_ENTREGA`). Esto garantiza que una vez completada la compra, si el usuario presiona "Atrás" desde la pantalla de confirmación, no vuelva accidentalmente al formulario de pedido ni vuelva a procesar la compra.

---

### 4. ¿Qué tuviste que corregir del código que te generó la IA para el buscador en tiempo real?
* **Respuesta:**
  Se realizaron las siguientes correcciones en el buscador:
  * Se aseguró que la búsqueda fuera **insensible a mayúsculas y minúsculas** mediante `contains(textoBusqueda, ignoreCase = true)`.
  * Se combinó la lógica de búsqueda con el filtro de categoría activo en una sola expresión condicional reactiva.
  * Se previno que búsquedas con espacios en blanco o texto vacío causaran inconsistencias en el renderizado del `LazyVerticalGrid`.

---

### 5. Compara el `NavigationDrawer` del Laboratorio 6 con el `NavigationBar` de esta tarea: ¿en qué caso usarías cada uno en un proyecto propio?
* **Respuesta:**
  * **`NavigationBar` (Barra inferior):** Lo usaría para navegar entre **3 y 5 secciones principales** de alta frecuencia de uso que tienen una jerarquía equivalente (ej. Inicio, Categorías, Pedidos, Perfil). Su principal ventaja es la ergonomía y rapidez de acceso directo con el pulgar en teléfonos móviles.
  * **`NavigationDrawer` (Menú lateral desplegable):** Lo usaría en aplicaciones con **más de 5 secciones**, o cuando se requiere acceder a opciones secundarias, configuraciones avanzadas, cambio de cuentas/roles, términos y soporte. Su ventaja es que no ocupa espacio visible constante en la pantalla y permite organizar categorías complejas en una lista vertical.
