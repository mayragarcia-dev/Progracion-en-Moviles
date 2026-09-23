package com.garcia.tecsupfit

data class Clase(
    val id: Int,
    val nombre: String,
    val horario: String,
    val sala: String = "Sala 1",
    val duracion: String = "45 min",
    val descripcion: String = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
    val cuposDisponibles: Int = 8,
    val cuposTotales: Int = 12,
    val estado: String = "Confirmada",
    val fecha: String = "Hoy",
)
