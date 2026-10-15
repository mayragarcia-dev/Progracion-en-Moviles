package com.tecsup.ayudacampus.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tickets",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("clienteId"), Index("categoriaId"), Index("estado"), Index("prioridad")]
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clienteId: Int,
    val categoriaId: Int,
    val titulo: String,
    val descripcion: String,
    val ubicacion: String,
    val prioridad: String,
    val estado: String,
    val fecha: Long,
    val fechaCierre: Long? = null,
    val calificacion: Int? = null
)
