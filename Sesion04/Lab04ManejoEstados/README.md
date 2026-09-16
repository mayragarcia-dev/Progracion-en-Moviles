\# Laboratorio 04 - Manejo de Estados en Jetpack Compose



\## Descripción



Aplicación Android desarrollada con Kotlin y Jetpack Compose para practicar el manejo de estados en una interfaz de usuario.



El laboratorio consiste en desarrollar una aplicación de lista de tareas donde el usuario puede registrar tareas, marcarlas como completadas y eliminarlas. La interfaz se actualiza automáticamente cuando cambia el estado de las tareas.



\## Objetivos



\- Comprender el manejo de estados en Jetpack Compose.

\- Utilizar `remember` y `mutableStateOf`.

\- Utilizar estados mutables para actualizar la interfaz.

\- Crear componentes reutilizables con `@Composable`.

\- Actualizar la interfaz cuando cambia el estado.

\- Utilizar `Checkbox` para cambiar el estado de una tarea.

\- Mostrar información calculada a partir del estado.

\- Utilizar `LazyColumn` para mostrar una lista de elementos.

\- Utilizar `@Preview` para visualizar la interfaz desde Android Studio.



\## Tecnologías utilizadas



\- Kotlin

\- Android Studio

\- Jetpack Compose

\- Material 3

\- Gradle



\## Desarrollo del laboratorio



\### 1. Ejercicio de manejo de estado



Se desarrolló inicialmente un ejercicio de temperatura utilizando `remember` y `mutableStateOf`.



La temperatura inicia en 20 °C y puede modificarse mediante botones:



\- Subir

\- Bajar

\- Resetear



También se utiliza un cambio de color dependiendo de la temperatura.



\### 2. Modelo de datos Tarea



Se creó el modelo `Tarea` con los siguientes atributos:



```kotlin

data class Tarea(

&#x20;   val id: Int,

&#x20;   val nombre: String,

&#x20;   val completada: Boolean = false

)

