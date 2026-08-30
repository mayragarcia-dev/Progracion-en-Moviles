package com.garcia.lab03bibliotecakotlin

import java.time.LocalDate

data class Prestamo(
    val libro: Libro,
    val usuario: Usuario,
    val fechaPrestamo: LocalDate,
    val fechaDevolucion: LocalDate,
    val fechaEntrega: LocalDate
)