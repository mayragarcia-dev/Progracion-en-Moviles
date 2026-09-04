# Laboratorio 03- Tarea - Registro de Notas

Aplicación Android desarrollada con **Kotlin** y **Jetpack Compose** para registrar las notas de cuatro cursos y calcular el promedio ponderado del estudiante.

Este proyecto corresponde a la **Semana 3 del curso de Programación en Móviles** y tiene como objetivo aplicar diferentes controles de interfaz utilizando Jetpack Compose.

---

## Tecnologías utilizadas

* Kotlin
* Android Studio
* Jetpack Compose
* Material 3
* Gradle

---

## Funcionalidades

La aplicación permite:

* Registrar notas mediante controles **Slider**.
* Ingresar notas en un rango de **0 a 20**.
* Mostrar el valor de la nota en tiempo real.
* Calcular el **promedio ponderado**.
* Redondear el promedio final mediante un **Switch**.
* Confirmar las notas mediante un **Checkbox**.
* Mantener deshabilitado el botón de cálculo hasta confirmar las notas.
* Mostrar los resultados mediante una **Card**.
* Mostrar una observación según el promedio obtenido.
* Utilizar diferentes colores según el resultado.
* Mostrar un mensaje de confirmación después de calcular.

---

## Cursos y pesos

| Curso                            | Peso |
| -------------------------------- | ---: |
| Fundamentos de Programación      |  20% |
| Programación Orientada a Objetos |  25% |
| Programación en Móviles          |  30% |
| Base de Datos                    |  25% |

---

## Cálculo del promedio

El promedio ponderado se obtiene utilizando los siguientes pesos:

```text
Promedio =
Nota Fundamentos × 0.20 +
Nota POO × 0.25 +
Nota Móviles × 0.30 +
Nota Base de Datos × 0.25
```

Si el usuario activa la opción **"Redondear promedio final"**, el resultado se redondea al entero más cercano.

---

## Clasificación del resultado

|    Promedio | Observación     |
| ----------: | --------------- |
|     17 - 20 | EXCELENTE       |
|  13 - 16.99 | APROBADO        |
|  10 - 12.99 | EN RECUPERACIÓN |
| Menor de 10 | DESAPROBADO     |

---

## Casos de prueba

### Caso 1 - APROBADO

| Curso                            | Nota |
| -------------------------------- | ---: |
| Fundamentos de Programación      |   15 |
| Programación Orientada a Objetos |   13 |
| Programación en Móviles          |   16 |
| Base de Datos                    |   14 |

**Promedio ponderado:** 14.55
**Promedio final:** 15
**Redondeo:** Activado
**Observación:** APROBADO

---

### Caso 2 - EN RECUPERACIÓN

| Curso                            | Nota |
| -------------------------------- | ---: |
| Fundamentos de Programación      |   12 |
| Programación Orientada a Objetos |   10 |
| Programación en Móviles          |   11 |
| Base de Datos                    |    9 |

**Promedio ponderado:** 10.45
**Promedio final:** 10.45
**Redondeo:** Desactivado
**Observación:** EN RECUPERACIÓN

---

### Caso 3 - EXCELENTE

| Curso                            | Nota |
| -------------------------------- | ---: |
| Fundamentos de Programación      |   18 |
| Programación Orientada a Objetos |   17 |
| Programación en Móviles          |   19 |
| Base de Datos                    |   18 |

**Promedio ponderado:** 18.05
**Promedio final:** 18
**Redondeo:** Activado
**Observación:** EXCELENTE

---

### Caso 4 - DESAPROBADO

| Curso                            | Nota |
| -------------------------------- | ---: |
| Fundamentos de Programación      |    8 |
| Programación Orientada a Objetos |    9 |
| Programación en Móviles          |    7 |
| Base de Datos                    |   10 |

**Promedio ponderado:** 8.45
**Promedio final:** 8.45
**Redondeo:** Desactivado
**Observación:** DESAPROBADO

---

## Interfaz de la aplicación

La aplicación cuenta con:

* Barra superior con el título **"Registro de Notas"**.
* Fondo con degradado suave.
* Sliders para cada curso.
* Switch para redondear el promedio.
* Checkbox para confirmar las notas.
* Botón **"CALCULAR PROMEDIO"**.
* Tarjeta de resultados.
* Indicador de observación por color.
* Mensaje de confirmación.
* Pie de página con el nombre del desarrollador.

---

## Capturas de pantalla

### Pantalla principal y Resultado del cálculo
<img width="1600" height="1600" alt="image" src="https://github.com/user-attachments/assets/0936ad73-7e78-449d-8899-42ea8d65bbc9" />


---

## Autora

**Mayra Julisa Garcia Rojas**

## Curso

**Programación en Móviles**

## Laboratorio

**Semana 3 - Registro de Notas**

