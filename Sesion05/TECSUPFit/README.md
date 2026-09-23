# TECSUP Fit

Aplicación móvil desarrollada con **Kotlin** y **Jetpack Compose** como parte de la sesión de Programación en Móviles.

El proyecto **TECSUP Fit** permite visualizar clases, consultar el detalle de una clase, realizar una reserva y consultar las reservas realizadas.

## Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* Android Studio
* Gradle

## Funcionalidades principales

### Inicio

* Lista de clases mediante `LazyColumn`.
* Filtros mediante `LazyRow`.
* Filtros disponibles:

  * Hoy
  * Esta semana
* Cada clase muestra su nombre y horario.

### Detalle de clase

* Muestra la información de la clase seleccionada.
* Recibe el ID de la clase mediante navegación.
* Permite reservar un cupo.

### Confirmación

* Muestra el resumen de la reserva.
* Presenta la clase y el horario seleccionado.
* Permite acceder a la pantalla de reservas.

### Reservas

* Lista de clases reservadas.
* Estados disponibles:

  * Confirmada
  * Completada
* Los estados se diferencian visualmente mediante componentes de Material 3.

### Perfil

* Muestra información del usuario.
* Incluye estadísticas simples relacionadas con las clases.

### Navegación

La aplicación cuenta con una barra de navegación inferior con cuatro opciones:

1. Inicio
2. Reservas
3. Rutinas
4. Perfil

El elemento correspondiente a la pantalla actual se muestra como activo.

## Flujo de navegación

```text
Inicio
  ↓
Detalle/{id}
  ↓
Confirmacion/{id}
  ↓
Reserva seleccionada
  ↓
Reservas
```

El ID de la clase permite mantener identificada la clase seleccionada durante el proceso de reserva.

## Mejoras realizadas con IA

Durante la fase de mejora se utilizó inteligencia artificial como apoyo para:

* Mejorar los filtros de la pantalla de Inicio.
* Organizar la navegación utilizando el ID de la clase.
* Mantener la reserva seleccionada.
* Mejorar visualmente los estados de las reservas.
* Revisar y corregir el código generado.
* Mantener buenas prácticas sin incorporar código avanzado innecesario.

Los prompts utilizados durante esta etapa se encuentran documentados en:

```text
PROMPTS.md
```

## Ramas del proyecto

El repositorio cuenta con las siguientes ramas:

```text
main
mejora-ia
```

La rama `main` contiene la implementación base del proyecto y la rama `mejora-ia` contiene las mejoras realizadas con apoyo de inteligencia artificial.

## Validación

El proyecto fue validado mediante la compilación:

```powershell
.\gradlew.bat assembleDebug
```

Resultado:

```text
BUILD SUCCESSFUL
```

## Evidencias

### Captura 1 — Código

<img width="1701" height="982" alt="image" src="https://github.com/user-attachments/assets/57f38fde-33eb-4b9a-835d-141fc17f4c95" />

### Captura 2 — Interfaz

<img width="558" height="728" alt="image" src="https://github.com/user-attachments/assets/31950b49-189a-4cc5-964e-2ed3cf3ea513" />

## Autora

**Mayra Julisa Garcia Rojas**

Proyecto académico — Programación en Móviles

**TECSUP — 2026**




