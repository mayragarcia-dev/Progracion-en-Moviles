# Registro de Prompts - Proyecto Mi Bodega (Área con IA)

**Rol asumido:** Tech Lead de Android  
**Objetivo:** Mejorar el código aplicando buenas prácticas de desarrollo Android y Jetpack Compose, manteniendo los mismos conceptos básicos sin sobrecomplicar la arquitectura ni alterar las interfaces requeridas.

---

## Prompt 1: Corrección de error crítico al crear cuenta (Solución de Crash)

* **Contexto / Solicitud del usuario:**
  > "El botón 'Crear cuenta' al presionarlo la app me bota"

* **Indicación / Prompt al Tech Lead:**
  > Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. Quiero que respondas en formato de código limpio. Ten en cuenta estas condiciones: No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada. Diagnostica por qué la aplicación se cierra al presionar el botón "Crear cuenta" y aplica las correcciones necesarias.

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
  > Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. Quiero que respondas en formato de código limpio. Ten en cuenta estas condiciones: No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada. Reemplaza los íconos vectoriales de las categorías en `InicioScreen.kt` por emojis representativos.

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
  > Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. Quiero que respondas en formato de código limpio. Ten en cuenta estas condiciones: No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada. Crea la pantalla de inicio de sesión (`LoginScreen.kt`) en Jetpack Compose respetando estrictamente el diseño de la imagen enviada.

* **Resultado / Acciones aplicadas por la IA:**
  1. Creación del archivo `LoginScreen.kt` en el paquete `ui.cliente.screens.login`.
  2. Implementación de estado local (`remember { mutableStateOf(...) }`) para el correo, la contraseña y la visibilidad de la misma (`VisualTransformation`).
  3. Registro de la constante `Rutas.LOGIN` en `Rutas.kt`.
  4. Integración en `AppNavegacion.kt` con navegación limpia hacia `Rutas.INICIO` y `Rutas.REGISTRO`.
