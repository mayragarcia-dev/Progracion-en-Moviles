package com.tecsup.ayudacampus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notificaciones")
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val mensaje: String,
    val ticketId: Int,
    val fecha: Long = System.currentTimeMillis(),
    val leida: Boolean = false
)
