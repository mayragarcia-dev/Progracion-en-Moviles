package com.tecsup.ayudacampus.ui.estudiante

import com.tecsup.ayudacampus.data.local.entity.CategoriaEntity
import com.tecsup.ayudacampus.data.local.model.TicketResumen

data class EstudianteUiState(
    val tickets: List<TicketResumen> = emptyList(),
    val categoriasActivas: List<CategoriaEntity> = emptyList(),
    val mensaje: String? = null
)
