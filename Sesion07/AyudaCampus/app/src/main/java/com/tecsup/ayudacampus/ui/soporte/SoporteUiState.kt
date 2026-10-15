package com.tecsup.ayudacampus.ui.soporte

import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen

data class SoporteUiState(
    val tickets: List<TicketResumen> = emptyList(),
    val resumen: ResumenPanel? = null,
    val alertasAltaAntiguedad: Int = 0,
    val mensaje: String? = null
)
