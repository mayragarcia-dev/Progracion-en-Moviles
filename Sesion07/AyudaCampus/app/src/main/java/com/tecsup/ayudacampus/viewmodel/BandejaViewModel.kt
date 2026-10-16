package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.Prioridad
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

enum class FiltroBandeja(val etiqueta: String) {
    TODOS("Todos"),
    ABIERTOS("Abiertos"),
    EN_PROCESO("En proceso"),
    RESUELTOS("Resueltos")
}

enum class FiltroPrioridadBandeja(val etiqueta: String, val prioridad: Prioridad?) {
    TODAS("Todas", null),
    ALTA("Alta", Prioridad.ALTA),
    MEDIA("Media", Prioridad.MEDIA),
    BAJA("Baja", Prioridad.BAJA)
}

data class EstadoSla(val etiqueta: String, val vencido: Boolean, val cumplido: Boolean)

class BandejaViewModel(private val repository: AyudaRepository) : ViewModel() {
    private val _filtroSeleccionado = MutableStateFlow(FiltroBandeja.TODOS)
    val filtroSeleccionado: StateFlow<FiltroBandeja> = _filtroSeleccionado.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    private val _prioridadSeleccionada = MutableStateFlow(FiltroPrioridadBandeja.TODAS)
    val prioridadSeleccionada: StateFlow<FiltroPrioridadBandeja> = _prioridadSeleccionada.asStateFlow()

    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)
    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada.asStateFlow()

    val categorias: StateFlow<List<String>> = repository.observarCategoriasActivas()
        .map { lista -> lista.map { it.nombre } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val todosLosTickets = repository.observarTicketsSoporte()

    val tickets: StateFlow<List<TicketResumen>> = combine(
        todosLosTickets, filtroSeleccionado, textoBusqueda, prioridadSeleccionada, categoriaSeleccionada
    ) { lista, filtro, texto, prioridad, categoria ->
        lista.filter { ticket ->
            val coincideEstado = when (filtro) {
                FiltroBandeja.TODOS -> true
                FiltroBandeja.ABIERTOS -> ticket.estado == EstadoTicket.ABIERTO.name
                FiltroBandeja.EN_PROCESO -> ticket.estado == EstadoTicket.EN_PROCESO.name
                FiltroBandeja.RESUELTOS -> ticket.estado == EstadoTicket.RESUELTO.name
            }
            val consulta = texto.trim().lowercase()
            val coincideTexto = consulta.isBlank() || listOf(
                ticket.id.toString(), ticket.titulo, ticket.descripcion, ticket.clienteNombre
            ).any { it.lowercase().contains(consulta) }
            coincideEstado && coincideTexto &&
                (prioridad.prioridad == null || ticket.prioridad == prioridad.prioridad.name) &&
                (categoria == null || ticket.categoriaNombre == categoria)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val reloj = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(60_000)
        }
    }

    val slaPorTicket: StateFlow<Map<Int, EstadoSla>> = combine(todosLosTickets, reloj) { lista, ahora ->
        lista.associate { ticket ->
            val duracionObjetivo = when (ticket.prioridad) {
                Prioridad.ALTA.name -> 4L * 60 * 60 * 1_000
                Prioridad.MEDIA.name -> 24L * 60 * 60 * 1_000
                else -> 72L * 60 * 60 * 1_000
            }
            val vencimiento = ticket.fecha + duracionObjetivo
            val fechaFinalizacion = when (ticket.estado) {
                EstadoTicket.RESUELTO.name, EstadoTicket.CERRADO.name -> ticket.fechaCierre ?: ahora
                else -> null
            }
            val restante = vencimiento - (fechaFinalizacion ?: ahora)
            val vencido = restante < 0
            val cumplido = fechaFinalizacion != null && !vencido
            val etiqueta = when {
                cumplido -> "SLA cumplido"
                fechaFinalizacion != null -> "SLA excedido por ${formatearRestante(-restante)}"
                restante >= 0 -> "SLA: ${formatearRestante(restante)} restantes"
                else -> "SLA vencido hace ${formatearRestante(-restante)}"
            }
            ticket.id to EstadoSla(etiqueta, vencido, cumplido)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyMap())

    private fun formatearRestante(milisegundos: Long): String {
        val minutos = milisegundos / 60_000
        val dias = minutos / (24 * 60)
        val horas = (minutos % (24 * 60)) / 60
        val mins = minutos % 60
        return when {
            dias > 0 -> "$dias d $horas h"
            horas > 0 -> "$horas h $mins min"
            else -> "$mins min"
        }
    }

    fun seleccionarFiltro(filtro: FiltroBandeja) {
        _filtroSeleccionado.value = filtro
    }

    fun cambiarBusqueda(texto: String) { _textoBusqueda.value = texto }
    fun seleccionarPrioridad(prioridad: FiltroPrioridadBandeja) { _prioridadSeleccionada.value = prioridad }
    fun seleccionarCategoria(categoria: String?) { _categoriaSeleccionada.value = categoria }
}

class BandejaViewModelFactory(
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandejaViewModel::class.java)) return BandejaViewModel(repository) as T
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
