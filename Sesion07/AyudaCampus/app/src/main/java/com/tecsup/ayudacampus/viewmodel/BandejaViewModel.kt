package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

enum class FiltroBandeja(val etiqueta: String) {
    TODOS("Todos"),
    ABIERTOS("Abiertos"),
    EN_PROCESO("En proceso"),
    RESUELTOS("Resueltos")
}

class BandejaViewModel(repository: AyudaRepository) : ViewModel() {
    private val _filtroSeleccionado = MutableStateFlow(FiltroBandeja.TODOS)
    val filtroSeleccionado: StateFlow<FiltroBandeja> = _filtroSeleccionado.asStateFlow()
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()
    private val _prioridadSeleccionada = MutableStateFlow<Prioridad?>(null)
    val prioridadSeleccionada: StateFlow<Prioridad?> = _prioridadSeleccionada.asStateFlow()
    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)
    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada.asStateFlow()

    val categorias: StateFlow<List<String>> = repository.observarCategoriasActivas()
        .map { lista -> lista.map { it.nombre }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val tickets: StateFlow<List<TicketResumen>> = combine(
        repository.observarTicketsSoporte(),
        filtroSeleccionado,
        busqueda,
        prioridadSeleccionada,
        categoriaSeleccionada
    ) { lista, filtro, texto, prioridad, categoria ->
        val porEstado = when (filtro) {
            FiltroBandeja.TODOS -> lista
            FiltroBandeja.ABIERTOS -> lista.filter { it.estado == EstadoTicket.ABIERTO.name }
            FiltroBandeja.EN_PROCESO -> lista.filter { it.estado == EstadoTicket.EN_PROCESO.name }
            FiltroBandeja.RESUELTOS -> lista.filter { it.estado == EstadoTicket.RESUELTO.name }
        }
        porEstado.filter { ticket ->
            val coincideTexto = texto.isBlank() || listOf(
                ticket.titulo, ticket.descripcion, ticket.clienteNombre,
                ticket.ubicacion, ticket.categoriaNombre
            ).any { it.contains(texto.trim(), ignoreCase = true) }
            val coincidePrioridad = prioridad == null || ticket.prioridad == prioridad.name
            val coincideCategoria = categoria == null || ticket.categoriaNombre == categoria
            coincideTexto && coincidePrioridad && coincideCategoria
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val slaPorTicket: StateFlow<Map<Int, EstadoSla>> = combine(
        repository.observarTicketsSoporte(),
        flow {
            while (true) {
                emit(System.currentTimeMillis())
                delay(60_000)
            }
        }
    ) { lista, ahora ->
        lista.associate { ticket ->
            val limiteMs = when (ticket.prioridad) {
                "ALTA" -> 4L * 60 * 60 * 1_000
                "MEDIA" -> 24L * 60 * 60 * 1_000
                else -> 72L * 60 * 60 * 1_000
            }
            val referencia = if (ticket.estado == EstadoTicket.RESUELTO.name || ticket.estado == EstadoTicket.CERRADO.name) {
                ticket.fechaCierre ?: ahora
            } else ahora
            val restante = limiteMs - (referencia - ticket.fecha).coerceAtLeast(0L)
            ticket.id to when {
                restante >= 0L -> EstadoSla("SLA: ${formatearTiempo(restante)} restantes", false)
                else -> EstadoSla("SLA excedido por ${formatearTiempo(-restante)}", true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyMap())

    fun seleccionarFiltro(filtro: FiltroBandeja) {
        _filtroSeleccionado.value = filtro
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun seleccionarPrioridad(prioridad: Prioridad?) {
        _prioridadSeleccionada.value = prioridad
    }

    fun seleccionarCategoria(categoria: String?) {
        _categoriaSeleccionada.value = categoria
    }

    private fun formatearTiempo(milisegundos: Long): String {
        val minutos = milisegundos / 60_000
        val horas = minutos / 60
        return if (horas >= 24) "${horas / 24} d ${horas % 24} h" else "${horas} h ${minutos % 60} min"
    }
}

data class EstadoSla(val etiqueta: String, val vencido: Boolean)

class BandejaViewModelFactory(
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandejaViewModel::class.java)) return BandejaViewModel(repository) as T
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
