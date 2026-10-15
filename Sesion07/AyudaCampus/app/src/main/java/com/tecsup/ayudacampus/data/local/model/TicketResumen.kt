package com.tecsup.ayudacampus.data.local.model

import androidx.room.ColumnInfo

data class TicketResumen(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val prioridad: String,
    val estado: String,
    val fecha: Long,
    val fechaCierre: Long?,
    val calificacion: Int?,
    @ColumnInfo(name = "clienteNombre") val clienteNombre: String,
    @ColumnInfo(name = "categoriaNombre") val categoriaNombre: String,
    @ColumnInfo(name = "categoriaIcono") val categoriaIcono: String,
    @ColumnInfo(name = "comentariosCount") val comentariosCount: Int
)

data class ResumenPanel(
    val total: Int,
    val abiertos: Int,
    @ColumnInfo(name = "enProceso") val enProceso: Int,
    val resueltos: Int,
    val cerrados: Int
)
