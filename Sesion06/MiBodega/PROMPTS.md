# Registro de Prompts - Proyecto Mi Bodega (Área con IA)

**Rol asumido:** Tech Lead de Android  
**Objetivo:** Mejorar el código aplicando buenas prácticas de desarrollo Android y Jetpack Compose, manteniendo los mismos conceptos básicos sin sobrecomplicar la arquitectura ni alterar las interfaces requeridas.

---

## Prompt 1: Corrección de error crítico al crear cuenta (Solución de Crash)

* **Contexto / Solicitud del usuario:**
  > "El botón 'Crear cuenta' al presionarlo la app me bota"

* **Indicación / Prompt al Tech Lead:**
  > Actúa como Tech Lead de Android. Diagnostica por qué la aplicación se cierra al presionar el botón "Crear cuenta". Aplica las correcciones necesarias con código limpio y siguiendo las mejores prácticas de Jetpack Compose y Android Manifest, sin alterar la estructura básica del proyecto.

* **Resultado / Acciones aplicadas por la IA:**
  1. Identificación de la causa raíz: Se intentaba cargar imágenes remotas mediante `AsyncImage` (Coil) en `ProductoCard` sin haber declarado `<uses-permission android:name="android.permission.INTERNET" />` en `AndroidManifest.xml`, lo que producía un `SecurityException` al navegar a la pantalla de Inicio.
  2. Adición del permiso de Internet en `AndroidManifest.xml`.
  3. Adición de imágenes de respaldo (`placeholder` y `error`) usando los recursos locales (`producto.imagenRes`) en `ProductoCard.kt`.
  4. Optimización del flujo de navegación en `AppNavegacion.kt` utilizando `popUpTo(Rutas.BIENVENIDA) { inclusive = true }`.

---

## Prompt 2: Personalización de Categorías con Emojis

* **Contexto / Solicitud del usuario:**
  > "colocar emojis aqui referentes" *(Acompañado de la imagen de la barra de categorías: Todos, Bebidas, Abarrotes, Snacks)*

* **Indicación / Prompt al Tech Lead:**
  > Actúa como Tech Lead de Android. Reemplaza los íconos vectoriales de las categorías en `InicioScreen.kt` por emojis representativos manteniendo el mismo diseño de los chips (`ChipCategoria`), colores del tema y tamaño sin agregar librerías adicionales.

* **Resultado / Acciones aplicadas por la IA:**
  * Modificación del componente `ChipCategoria` en `InicioScreen.kt`.
  * Asignación de emojis temáticos:
    * `🛍️` para **Todos**
    * `🥤` para **Bebidas**
    * `🥫` para **Abarrotes**
    * `🍿` para **Snacks**
  * Ajuste del tamaño de fuente a `24.sp` para mantener la proporción visual correcta con los chips.

---

## Prompt 3: Implementación de Pantalla de Login (Correo y Contraseña)

* **Contexto / Solicitud del usuario:**
  > "tambien hacer esta pantalla que pida correo y contraseña" *(Acompañado del mockup visual de la pantalla de Iniciar sesión)*

* **Indicación / Prompt al Tech Lead:**
  > Actúa como Tech Lead de Android. Necesito que crees la pantalla de inicio de sesión (`LoginScreen.kt`) en Jetpack Compose respetando estrictamente el diseño de la imagen enviada. Debe incluir:
  > - Encabezado con flecha de retorno y título "Iniciar sesión".
  > - Subtítulo descriptivo.
  > - Campo de correo electrónico con su respectivo ícono.
  > - Campo de contraseña con ícono de candado y toggle para mostrar/ocultar contraseña (ojo).
  > - Botón principal "Iniciar sesión".
  > - Enlace inferior "¿No tienes cuenta? Crear cuenta".
  > Mantén el código limpio, reutilizando componentes existentes y registrando la ruta en `Rutas.kt` y `AppNavegacion.kt`.

* **Resultado / Acciones aplicadas por la IA:**
  1. Creación del archivo `LoginScreen.kt` en el paquete `ui.cliente.screens.login`.
  2. Implementación de estado local (`remember { mutableStateOf(...) }`) para el correo, la contraseña y la visibilidad de la misma (`VisualTransformation`).
  3. Registro de la constante `Rutas.LOGIN` en `Rutas.kt`.
  4. Integración en `AppNavegacion.kt` con navegación limpia hacia `Rutas.INICIO` y `Rutas.REGISTRO`.

---

## Prompt 4: Preguntas de Reflexión para README.md

* **Contexto / Solicitud del usuario:**
  > "nos falta crear el readme VI. Preguntas de reflexión..."

* **Indicación / Prompt al Tech Lead:**
  > Actúa como Tech Lead de Android. Redacta las respuestas a las 5 preguntas de reflexión del laboratorio para el archivo `README.md` de forma clara, técnica y concisa, explicando los conceptos de maquetación en Compose, reactividad del estado, navegación con `popUpTo` y diferencias entre componentes UI.

* **Resultado / Acciones aplicadas por la IA:**
  * Creación del archivo `README.md` con las respuestas detalladas sobre:
    1. Diferencia entre modelos/actividades entregadas y esqueletos de pantallas UI.
    2. Reactividad y recomposición en Compose mediante `remember` y `mutableStateOf`.
    3. Manejo de la pila de navegación (*backstack*) con `navigate()` y `popUpTo`.
    4. Ajustes aplicados al buscador en tiempo real.
    5. Comparativa entre `NavigationBar` y `NavigationDrawer`.
