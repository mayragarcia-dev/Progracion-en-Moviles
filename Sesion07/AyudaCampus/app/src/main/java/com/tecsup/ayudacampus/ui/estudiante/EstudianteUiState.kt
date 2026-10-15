package com.tecsup.ayudacampus.ui.estudiante

import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.model.TicketResumen

data class EstudianteUiState(
    val tickets: List<TicketResumen> = emptyList(),
    val categoriasActivas: List<Categoria> = emptyList(),
    val mensaje: String? = null
)
