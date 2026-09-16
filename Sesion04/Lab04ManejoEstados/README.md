# Laboratorio 04 - Manejo de Estados en Jetpack Compose

## Descripción

Aplicación Android desarrollada con Kotlin y Jetpack Compose para practicar el manejo de estados en una interfaz de usuario.

El laboratorio consiste en desarrollar una aplicación de lista de tareas donde el usuario puede registrar tareas, marcarlas como completadas y eliminarlas. La interfaz se actualiza automáticamente cuando cambia el estado de las tareas.

## Objetivos

* Comprender el manejo de estados en Jetpack Compose.
* Utilizar `remember` y `mutableStateOf`.
* Utilizar estados mutables para actualizar la interfaz.
* Crear componentes reutilizables con `@Composable`.
* Actualizar la interfaz cuando cambia el estado.
* Utilizar `Checkbox` para cambiar el estado de una tarea.
* Mostrar información calculada a partir del estado.
* Utilizar `LazyColumn` para mostrar una lista de elementos.
* Utilizar `@Preview` para visualizar la interfaz desde Android Studio.

## Tecnologías utilizadas

* Kotlin
* Android Studio
* Jetpack Compose
* Material 3
* Gradle

## Desarrollo del laboratorio

### 1. Ejercicio de manejo de estado

Se desarrolló inicialmente un ejercicio de temperatura utilizando `remember` y `mutableStateOf`.
## Captura de pantalla
<img width="1464" height="1599" alt="image" src="https://github.com/user-attachments/assets/6b56fd7a-21ba-4ca3-b2c6-51cdc6f67c4c" />

La temperatura inicia en 20 °C y puede modificarse mediante botones:

* Subir
* Bajar
* Resetear

También se utiliza un cambio de color dependiendo de la temperatura:

* Rojo cuando la temperatura es mayor a 30 °C.
* Azul cuando la temperatura es menor a 10 °C.
* Negro para los demás valores.
## Captura de pantalla
<img width="1600" height="1156" alt="image" src="https://github.com/user-attachments/assets/0436e6ef-c5eb-46cf-8e87-d747eee7132d" />

### 2. Modelo de datos Tarea

Se creó el modelo `Tarea` con los siguientes atributos:

```kotlin
data class Tarea(
    val id: Int,
    val nombre: String,
    val completada: Boolean = false
)
```

El atributo `id` permite identificar cada tarea.

El atributo `nombre` almacena el nombre o descripción de la tarea.

El atributo `completada` permite controlar si la tarea está pendiente o completada.

### 3. Registro de tareas

Se agregó un campo de texto utilizando `OutlinedTextField` para que el usuario pueda ingresar una nueva tarea.

También se agregó el botón **"Agregar tarea"**.

Al presionar el botón, la tarea se agrega a la lista y el campo de texto se limpia automáticamente.

No se permite agregar una tarea si el campo está vacío.

### 4. Lista de tareas

Las tareas se muestran utilizando `LazyColumn`.

Cada tarea se presenta mediante un componente reutilizable llamado `ItemTarea`.

Este componente permite mostrar:

* Nombre de la tarea.
* Estado de la tarea.
* Checkbox para marcarla como completada.
* Botón para eliminarla.

### 5. Manejo de tareas completadas

Se utilizó un `Checkbox` para cambiar el estado de cada tarea.

Cuando una tarea es marcada como completada:

* El texto cambia a color gris.
* El texto aparece tachado.
* El contador de tareas completadas se actualiza.

Cuando se desmarca, vuelve a mostrarse como una tarea pendiente.

### 6. Contadores

La aplicación muestra información sobre el estado actual de la lista:

```text
Total de tareas: X
Tareas completadas: X
```

El valor de estos contadores se actualiza automáticamente cuando se agregan, completan o eliminan tareas.

### 7. Eliminación de tareas

Cada tarea cuenta con un botón **"Eliminar"**.

Al presionarlo, la tarea se elimina de la lista y la interfaz se actualiza automáticamente.

Los contadores también se actualizan después de eliminar una tarea.

### 8. Estado cuando no existen tareas

Cuando la lista está vacía, se muestra el mensaje:

```text
No hay tareas registradas
```

Este mensaje desaparece automáticamente cuando se agrega una tarea.

### 9. Vista previa

Se agregó una función `@Preview` para visualizar la pantalla de tareas directamente desde Android Studio sin necesidad de ejecutar la aplicación en un dispositivo.

## Manejo de estados utilizado

Durante el desarrollo se utilizaron diferentes mecanismos de estado de Jetpack Compose:

### `remember`

Permite conservar el valor de una variable durante las recomposiciones de la interfaz.

Ejemplo:

```kotlin
var textoTarea by remember { mutableStateOf("") }
```

### `mutableStateOf`

Permite crear un estado observable. Cuando su valor cambia, Compose actualiza automáticamente los elementos de la interfaz que dependen de ese estado.

### `mutableStateListOf`

Se utilizó para mantener la lista de tareas:

```kotlin
val listaTareas = remember { mutableStateListOf<Tarea>() }
```

Esto permite agregar, modificar y eliminar tareas mientras la interfaz se actualiza.

## Funcionamiento de la aplicación

El flujo principal de la aplicación es:

1. El usuario escribe una tarea.
2. Presiona **"Agregar tarea"**.
3. La tarea aparece en la lista.
4. El usuario puede marcarla como completada.
5. La tarea completada cambia de apariencia.
6. Los contadores se actualizan.
7. El usuario puede eliminar una tarea.
8. Si no existen tareas, se muestra el mensaje correspondiente.

## Captura de pantalla
<img width="720" height="1600" alt="image" src="https://github.com/user-attachments/assets/2bf5978c-5b45-45c1-b603-7c284b1c44e4" />

## Pruebas realizadas

Se verificó que la aplicación pueda:

* Registrar nuevas tareas.
* Evitar el registro de tareas vacías.
* Mostrar las tareas registradas.
* Marcar tareas como completadas.
* Cambiar visualmente las tareas completadas.
* Mostrar el total de tareas.
* Mostrar el total de tareas completadas.
* Eliminar tareas.
* Mostrar un mensaje cuando la lista está vacía.
* Ejecutarse correctamente en un dispositivo Android.

La compilación del proyecto se realizó correctamente mediante Gradle.

## Estructura principal

```text
Lab04ManejoEstados/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── garcia/
│                       └── lab04manejoestados/
│                           └── MainActivity.kt
├── capturas/
├── README.md
└── build.gradle.kts
```
## Promnt
<img width="748" height="865" alt="image" src="https://github.com/user-attachments/assets/c9d160e6-b610-4a09-9399-25688502dbfb" />

## Repositorio

Repositorio del proyecto:

https://github.com/mayragarcia-dev/Progracion-en-Moviles.git




