package com.tecsup.ayudacampus.data.local.model

import androidx.room.ColumnInfo

data class ConteoCategoria(
    @ColumnInfo(name = "categoriaId") val categoriaId: Int,
    val nombre: String,
    val cantidad: Int
)
