package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Notificacion
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.model.DatoGrafico
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val ColoresEstado = listOf(0xFFF59E0B, 0xFF2563EB, 0xFF16A34A, 0xFF9CA3AF)
private val ColoresCategoria = listOf(0xFF8B5CF6, 0xFF8B5CF6, 0xFF7C3AED, 0xFFC4B5FD, 0xFFA78BFA)

class PanelViewModel(private val repository: AyudaRepository) : ViewModel() {
    private val resumen: StateFlow<ResumenPanel> = repository.observarResumenPanel()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ResumenPanel(0, 0, 0, 0, 0))

    val abiertos: StateFlow<Int> = resumen.map { it.abiertos }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val enProceso: StateFlow<Int> = resumen.map { it.enProceso }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    val resueltos: StateFlow<Int> = resumen.map { it.resueltos }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val porEstado: StateFlow<List<DatoGrafico>> = resumen.map { it.toDatosGrafico() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val porCategoria: StateFlow<List<DatoGrafico>> = repository.observarCantidadPorCategoria()
        .map { conteos ->
            conteos.mapIndexed { indice, conteo ->
                DatoGrafico(
                    etiqueta = conteo.nombre,
                    valor = conteo.cantidad,
                    color = ColoresCategoria[indice % ColoresCategoria.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val altasSinAtender: StateFlow<Int> = repository.observarAltasSinAtender(
        System.currentTimeMillis() - 60 * 60 * 1_000L
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val promedioResolucion: StateFlow<String?> = repository.observarPromedioResolucion()
        .map { promedioMs -> promedioMs?.toLong()?.let(::formatearDuracion) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val notificaciones: StateFlow<List<Notificacion>> = repository.observarNotificaciones()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun marcarNotificacionesLeidas() {
        viewModelScope.launch {
            repository.marcarNotificacionesLeidas()
        }
    }

    private fun formatearDuracion(milisegundos: Long): String {
        val minutos = (milisegundos / 60_000).coerceAtLeast(0)
        val dias = minutos / (24 * 60)
        val horas = (minutos % (24 * 60)) / 60
        val minutosRestantes = minutos % 60
        return when {
            dias > 0 -> "$dias d $horas h"
            horas > 0 -> "$horas h $minutosRestantes min"
            else -> "$minutosRestantes min"
        }
    }

    private fun ResumenPanel.toDatosGrafico(): List<DatoGrafico> = listOf(
        DatoGrafico("Abierto", abiertos, ColoresEstado[0]),
        DatoGrafico("En proceso", enProceso, ColoresEstado[1]),
        DatoGrafico("Resuelto", resueltos, ColoresEstado[2]),
        DatoGrafico("Cerrado", cerrados, ColoresEstado[3])
    )
}

class PanelViewModelFactory(
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PanelViewModel::class.java)) return PanelViewModel(repository) as T
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
