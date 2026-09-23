# Clínica Salud+

Aplicación móvil desarrollada con **Kotlin, Jetpack Compose y Material 3** como parte de la sesión de Programación en Móviles.

## Descripción

**Clínica Salud+** permite consultar médicos y especialidades, visualizar el perfil de un médico, agendar una cita y consultar las citas e historial médico.

La aplicación utiliza una implementación sencilla, sin bases de datos ni arquitecturas avanzadas, facilitando su comprensión y explicación académica.

## Funcionalidades

* Pantalla de inicio.
* Especialidades mediante `LazyRow` con chips.
* Lista de médicos mediante `LazyColumn`.
* Perfil del médico con navegación y parámetros.
* Agendamiento de citas.
* Selección de fecha y hora.
* Confirmación de la cita agendada.
* Menú lateral mediante `Navigation Drawer`.
* Pantalla Mis citas.
* Pantalla Historial médico.
* Interfaz desarrollada con Jetpack Compose y Material 3.

## Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* Android Studio
* Gradle

## Estructura principal

```text
ClinicaSalud2/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/garcia/clinicasalud/
│           │       ├── MainActivity.kt
│           │       └── ui/
│           │           └── theme/
│           └── res/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Evidencias

### 1. Código de la aplicación

![Captura del código](capturas/codigo.png)

### 2. Pantallas de la aplicación

![Captura de la aplicación](capturas/aplicacion.png)

## Ejecución

1. Abrir el proyecto en Android Studio.
2. Esperar la sincronización de Gradle.
3. Seleccionar un emulador o dispositivo Android.
4. Ejecutar la aplicación.

## Autora

**Mayra Julisa Garcia Rojas**

Proyecto académico - Programación en Móviles.
