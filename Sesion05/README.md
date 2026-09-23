# Sesión 05 - Navegación con Jetpack Compose

## Descripción

En esta sesión se implementó una aplicación Android utilizando **Kotlin** y **Jetpack Compose**, enfocada en el manejo de navegación entre diferentes pantallas mediante **Navigation Compose**.

El proyecto permite navegar entre una pantalla principal, una lista de elementos, una pantalla de detalle y un perfil de usuario.

## Tecnologías utilizadas

* Kotlin
* Android Studio
* Jetpack Compose
* Material 3
* Navigation Compose
* Gradle
* Git y GitHub

## Estructura del proyecto

```text
NavLab/
└── app/
    └── src/
        └── main/
            └── java/
                └── com.tuapp.navlab/
                    ├── MainActivity.kt
                    ├── model/
                    │   └── Student.kt
                    ├── navigation/
                    │   ├── AppNavigation.kt
                    │   └── Screen.kt
                    └── screens/
                        ├── DetailScreen.kt
                        ├── HomeScreen.kt
                        ├── ListScreen.kt
                        ├── LoginScreen.kt
                        └── ProfileScreen.kt
```

## Pantallas implementadas

### LoginScreen

Pantalla de inicio de sesión de la aplicación.

### HomeScreen

Pantalla principal desde donde se puede acceder a la lista de elementos y al perfil del usuario.

### ListScreen

Muestra una lista de elementos utilizando `LazyColumn`. Cada elemento permite acceder a su respectiva pantalla de detalle.

### DetailScreen

Muestra el identificador del elemento seleccionado mediante un argumento dinámico:

```text
detail/{itemId}
```

### ProfileScreen

Muestra la información del perfil del usuario y permite regresar al inicio utilizando `popUpTo` para limpiar el back stack.

## Navegación

Las rutas principales utilizadas son:

```text
home
list
profile
detail/{itemId}
```

La navegación se centraliza en `AppNavigation.kt` y las rutas se definen en `Screen.kt`.

## Modelo de datos

El proyecto incluye el modelo `Student.kt`, utilizado para representar los datos del estudiante/usuario.

## Flujo de navegación

```text
Home
 ├── Lista
 │    └── Detalle
 │
 └── Perfil
      └── Inicio
```

---

# Evidencias

## 1. Captura del código

En esta captura se muestra parte del código implementado para la navegación entre las diferentes pantallas.

<img width="1919" height="1010" alt="image" src="https://github.com/user-attachments/assets/f1401d38-50ef-430a-8e45-3362cc46e2c6" />


## 2. Captura de la aplicación

En esta captura se muestra la aplicación ejecutándose en el emulador y la navegación implementada.

<img width="508" height="741" alt="image" src="https://github.com/user-attachments/assets/98fd9191-9f69-4d91-bf1c-6087276b1dd4" />

## 3. Prompt de mejora
<img width="1275" height="795" alt="image" src="https://github.com/user-attachments/assets/c94f63b3-7f78-4429-b645-8c2a45b0db14" />


---

## Requisitos

Para ejecutar el proyecto se necesita:

* Android Studio.
* JDK compatible con la versión de Gradle utilizada.
* Android SDK.
* Un dispositivo físico o emulador Android.

## Ejecución

1. Abrir el proyecto `NavLab` en Android Studio.
2. Esperar a que termine la sincronización de Gradle.
3. Seleccionar un dispositivo Android o iniciar un emulador.
4. Presionar **Run ▶**.
5. Probar la navegación entre las diferentes pantallas.

## Git

Los cambios de la sesión fueron registrados mediante commits separados para mantener un historial organizado.

Se implementaron:

* Modelo `Student`.
* Pantalla de inicio de sesión.
* Rutas de navegación.
* `AppNavigation`.
* Navegación desde `MainActivity`.
* Pantalla de detalle.
* Pantalla principal.
* Lista de elementos.
* Perfil de usuario.
* Limpieza del back stack.

## Autor

**Mayra Garcia Rojas**

Proyecto académico de Programación en Móviles.
