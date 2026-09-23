# Registro de Prompts - Proyecto TECSUP Fit

Este archivo documenta las instrucciones (prompts) utilizadas con la IA para la refactorización, corrección de errores y mejora del diseño UI/UX de la aplicación móvil **TECSUP Fit** desarrollada en **Jetpack Compose** y **Material 3**.

---

## 📌 Prompt 1: Corrección de errores iniciales del proyecto

> **Solicitud:**  
> *"ayudame a corregir los errores"*

### 🛠️ Acciones realizadas por la IA:
* Diagnóstico del fallo `OutOfMemoryError: Java heap space` en la tarea `:app:mergeExtDexDebug`.
* Creación del archivo `gradle.properties` en la raíz del proyecto configurando `org.gradle.jvmargs=-Xmx2048m`.
* Actualización del catálogo de versiones (`libs.versions.toml`) e `app/build.gradle.kts` para resolver dependencias de Jetpack Compose, Íconos de Material Extendidos y Navigation Compose.

---

## 📌 Prompt 2: Verificación de Requisitos de Perfil y BottomBar

> **Solicitud:**  
> *"Comprobar que PerfilScreen realmente tenga datos del usuario y estadísticas simples (clases tomadas, racha). Explicar cómo el BottomBar sabe qué ícono resaltar y por qué la selección de horario actúa como un RadioButton."*

### 🛠️ Acciones realizadas por la IA:
* Verificación y enriquecimiento visual de `PerfilScreen` con tarjetas de usuario y estadísticas (*14 Clases*, *3 Rachas*).
* Explicación técnica de la propiedad `selected = (currentRoute == item.first)` apoyada en `navController.currentBackStackEntryAsState()`.
* Demostración de exclusividad mutua mediante la variable de estado `mutableStateOf` conectada a `FilterChip`.

---

## 📌 Prompt 3: Rediseño de la Pantalla Inicio como Tech Lead Android

> **Solicitud:**  
> *"Actúa como Tech Lead de Android especializado en Jetpack Compose. Necesito que mejores el código de la pantalla Inicio de mi aplicación TECSUP Fit, aplicando buenas prácticas de organización y manteniendo el diseño actual de la interfaz. Quiero agregar filtros para las clases mediante un LazyRow, utilizando las opciones 'Hoy' y 'Esta semana'. Los filtros deben utilizar componentes de Material 3 y permitir identificar claramente cuál está seleccionado."*

### 🛠️ Acciones realizadas por la IA:
* Implementación de banner superior verde con esquinas redondeadas.
* Integración de `LazyRow` con `FilterChip` de Material 3 indicando visualmente el estado activo.
* Lista de clases con contenedor de ícono de mancuerna (`FitnessCenter`) sobre fondo verde claro (`primaryContainer`).
* Creación de la paleta de colores corporativa en `Color.kt` y `Theme.kt`.

---

## 📌 Prompt 4: Personalización del Usuario

> **Solicitud:**  
> *"Que no sea Diego Ramos sino Mayra García"*

### 🛠️ Acciones realizadas por la IA:
* Actualización del saludo en la pantalla de Inicio: `"Hola, Mayra"`.
* Actualización del nombre completo en la pantalla de Perfil: `"Mayra García"`.
* Actualización del círculo de avatar con las iniciales: `"MG"`.

---

## ⚙️ Resumen de Tecnologías Aplicadas
- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Navegación:** Navigation Compose (`NavHost`, `rememberNavController`)
- **Gestión de Estado:** `remember`, `mutableStateOf`
- **Layouts:** `Scaffold`, `LazyRow`, `LazyColumn`, `Card`, `FilterChip`, `NavigationBar`
