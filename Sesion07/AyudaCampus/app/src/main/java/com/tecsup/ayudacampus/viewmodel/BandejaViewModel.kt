package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    val tickets: StateFlow<List<TicketResumen>> = combine(
        repository.observarTicketsSoporte(), filtroSeleccionado
    ) { lista, filtro ->
        when (filtro) {
            FiltroBandeja.TODOS -> lista
            FiltroBandeja.ABIERTOS -> lista.filter { it.estado == EstadoTicket.ABIERTO.name }
            FiltroBandeja.EN_PROCESO -> lista.filter { it.estado == EstadoTicket.EN_PROCESO.name }
            FiltroBandeja.RESUELTOS -> lista.filter { it.estado == EstadoTicket.RESUELTO.name }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun seleccionarFiltro(filtro: FiltroBandeja) {
        _filtroSeleccionado.value = filtro
    }
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
