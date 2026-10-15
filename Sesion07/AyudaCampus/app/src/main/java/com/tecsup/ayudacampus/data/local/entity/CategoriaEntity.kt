package com.tecsup.ayudacampus.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categorias", indices = [Index(value = ["nombre"], unique = true)])
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val icono: String,
    val activa: Boolean = true
)
