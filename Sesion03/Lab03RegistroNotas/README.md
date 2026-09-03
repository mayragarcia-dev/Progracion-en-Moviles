\# Laboratorio 03 - Registro de Notas



\## Descripción



Aplicación Android desarrollada con Kotlin y Jetpack Compose para registrar las notas de cuatro cursos y calcular el promedio ponderado del estudiante.



El proyecto corresponde a la Semana 3 del curso de Programación en Móviles y tiene como objetivo aplicar nuevos controles de interfaz en Jetpack Compose.



\## Tecnologías utilizadas



\- Kotlin

\- Android Studio

\- Jetpack Compose

\- Material 3

\- Gradle



\## Funcionalidades



La aplicación permite:



\- Registrar notas mediante controles Slider.

\- Ingresar notas en un rango de 0 a 20.

\- Mostrar el valor de la nota en tiempo real.

\- Calcular un promedio ponderado.

\- Redondear el promedio final mediante un Switch.

\- Confirmar las notas mediante un Checkbox.

\- Deshabilitar el botón de cálculo hasta confirmar las notas.

\- Mostrar el resultado mediante una tarjeta.

\- Mostrar una observación según el promedio obtenido.

\- Mostrar diferentes colores según el resultado.

\- Mostrar un mensaje de confirmación después de calcular.



\## Cursos y pesos



| Curso | Peso |

|---|---:|

| Fundamentos de Programación | 20% |

| Programación Orientada a Objetos | 25% |

| Programación en Móviles | 30% |

| Base de Datos | 25% |



\## Cálculo del promedio



El promedio ponderado se calcula utilizando los siguientes pesos:



```text

Promedio = 

Nota Fundamentos × 0.20 +

Nota POO × 0.25 +

Nota Móviles × 0.30 +

Nota Base de Datos × 0.25

