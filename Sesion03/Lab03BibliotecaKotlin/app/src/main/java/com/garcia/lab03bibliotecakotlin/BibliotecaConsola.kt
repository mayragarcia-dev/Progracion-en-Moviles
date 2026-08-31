package com.garcia.lab03bibliotecakotlin

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun pedirDato(mensaje: String): String {
    print(mensaje)
    return System.`in`.bufferedReader().readLine()
}

fun main() {

    println("========================================")
    println("         SISTEMA DE BIBLIOTECA")
    println("========================================")
    println("Registro de prestamo y calculo de multas")
    println("Ingrese los datos solicitados para registrar el prestamo.")

    val titulo = pedirDato("Ingrese titulo del libro: ")

    val nombreUsuario = pedirDato("Ingrese nombre del usuario: ")

    println()
    println("Seleccione el tipo de usuario:")
    println("1. Alumno")
    println("2. Docente")

    val opcionUsuario = pedirDato("Ingrese una opcion (1 o 2): ")

    val tipoUsuario = when (opcionUsuario) {
        "1" -> "Alumno"
        "2" -> "Docente"
        else -> {
            println("Opcion no valida. Se registrara como Alumno.")
            "Alumno"
        }
    }

    // Formato de fecha: AAAA/MM/DD
    val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val fechaPrestamo = LocalDate.parse(
        pedirDato("Ingrese fecha de prestamo (DD/MM/AAAA): "),
        formatoFecha
    )

    val fechaDevolucion = LocalDate.parse(
        pedirDato("Ingrese fecha de devolucion (DD/MM/AAAA):"),
        formatoFecha
    )

    val fechaEntrega = LocalDate.parse(
        pedirDato("Ingrese fecha de entrega (DD/MM/AAAA): "),
        formatoFecha
    )
    if (fechaEntrega.isBefore(fechaPrestamo)) {
        println("Error: la fecha de entrega no puede ser anterior a la fecha de préstamo.")
        return
    }

    val libro = Libro(
        titulo = titulo
    )

    val usuario = Usuario(
        nombre = nombreUsuario,
        tipo = tipoUsuario
    )

    val prestamo = Prestamo(
        libro = libro,
        usuario = usuario,
        fechaPrestamo = fechaPrestamo,
        fechaDevolucion = fechaDevolucion,
        fechaEntrega = fechaEntrega
    )

    // Calcular días de retraso
    val diasRetraso =
        if (prestamo.fechaEntrega.isAfter(prestamo.fechaDevolucion)) {
            ChronoUnit.DAYS.between(
                prestamo.fechaDevolucion,
                prestamo.fechaEntrega
            )
        } else {
            0L
        }

    // Multa según tipo de usuario
    val multaPorDia =
        if (usuario.tipo.equals("Alumno", ignoreCase = true)) {
            1.50
        } else {
            3.00
        }

    val totalMulta = diasRetraso * multaPorDia

    val multa = Multa(
        diasRetraso = diasRetraso,
        multaPorDia = multaPorDia,
        total = totalMulta
    )

    // Mostrar resultados
    println()
    println("========================================")
    println("         DATOS DEL PRESTAMO")
    println("========================================")

    println("Libro: ${prestamo.libro.titulo}")
    println("Usuario: ${prestamo.usuario.nombre}")
    println("Tipo de usuario: ${prestamo.usuario.tipo}")
    println("Fecha de prestamo: ${prestamo.fechaPrestamo.format(formatoFecha)}")
    println("Fecha de devolucion: ${prestamo.fechaDevolucion.format(formatoFecha)}")
    println("Fecha de entrega: ${prestamo.fechaEntrega.format(formatoFecha)}")

    println("----------------------------------------")

    if (multa.diasRetraso > 0) {
        println("Estado: Devuelto con ${multa.diasRetraso} dias de retraso")
    } else {
        println("Estado: Devuelto en el dia oportuno")
    }

    println("----------------------------------------")
    println("Dia    Fecha          Multa       Acumulado")
    println("----------------------------------------")

    var acumulado = 0.0

    for (dia in 1..multa.diasRetraso.toInt()) {

        val fecha = prestamo.fechaDevolucion.plusDays(dia.toLong())

        acumulado += multa.multaPorDia

        println(
            String.format(
                "%-6d %-14s S/ %-8.2f S/ %.2f",
                dia,
                fecha.format(formatoFecha),
                multa.multaPorDia,
                acumulado
            )
        )
    }

    println("----------------------------------------")
    println("Total de dias de retraso: ${multa.diasRetraso}")
    println("Multa por dia: S/ %.2f".format(multa.multaPorDia))
    println("TOTAL DE MULTA: S/ %.2f".format(multa.total))
    println("========================================")
}