package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

enum class FiltroTickets(val etiqueta: String) {
    TODOS("Todos"),
    ABIERTOS("Abiertos"),
    EN_PROCESO("En proceso"),
    RESUELTOS("Resueltos")
}

class MisTicketsViewModel(
    val usuario: Usuario,
    repository: AyudaRepository
) : ViewModel() {
    private val _filtroSeleccionado = MutableStateFlow(FiltroTickets.TODOS)
    val filtroSeleccionado: StateFlow<FiltroTickets> = _filtroSeleccionado.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    private val ticketsCliente = repository.observarTicketsCliente(usuario.id)

    val tickets: StateFlow<List<TicketResumen>> = combine(
        ticketsCliente,
        filtroSeleccionado,
        textoBusqueda
    ) { lista, filtro, texto ->
        lista.filter { ticket ->
            val coincideEstado = when (filtro) {
                FiltroTickets.TODOS -> true
                FiltroTickets.ABIERTOS -> ticket.estado == EstadoTicket.ABIERTO.name
                FiltroTickets.EN_PROCESO -> ticket.estado == EstadoTicket.EN_PROCESO.name
                FiltroTickets.RESUELTOS -> ticket.estado == EstadoTicket.RESUELTO.name
            }
            val consulta = texto.trim().removePrefix("#")
            val numeroTicket = ticket.id.toString().padStart(4, '0')
            val coincideBusqueda = consulta.isEmpty() ||
                ticket.titulo.contains(consulta, ignoreCase = true) ||
                ticket.id.toString().contains(consulta, ignoreCase = true) ||
                numeroTicket.contains(consulta, ignoreCase = true)
            coincideEstado && coincideBusqueda
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val cantidadAbiertos: StateFlow<Int> = ticketsCliente
        .map { lista -> lista.count { it.estado == EstadoTicket.ABIERTO.name } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun seleccionarFiltro(filtro: FiltroTickets) {
        _filtroSeleccionado.value = filtro
    }

    fun buscar(texto: String) {
        _textoBusqueda.value = texto
    }
}

class MisTicketsViewModelFactory(
    private val usuario: Usuario,
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MisTicketsViewModel::class.java)) {
            return MisTicketsViewModel(usuario, repository) as T
        }
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
