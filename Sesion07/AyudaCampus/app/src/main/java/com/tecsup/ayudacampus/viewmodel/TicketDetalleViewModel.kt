package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.RolesUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TicketDetalleViewModel(
    private val ticketId: Int,
    val usuarioActual: Usuario,
    private val repository: AyudaRepository
) : ViewModel() {
    val ticket: StateFlow<TicketConDetalle?> = repository.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _calificacion = MutableStateFlow(0)
    val calificacion: StateFlow<Int> = _calificacion.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun seleccionarCalificacion(valor: Int) {
        if (valor in 1..5 && ticket.value?.ticket?.estado == EstadoTicket.RESUELTO.name) {
            _calificacion.value = valor
            _error.value = null
        }
    }

    fun comentar(texto: String) {
        val actual = ticket.value?.ticket ?: return
        val contenido = texto.trim()
        if (contenido.isEmpty() || actual.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch {
            _error.value = null
            runCatching { repository.agregarComentario(ticketId, usuarioActual, contenido) }
                .onFailure { _error.value = "No se pudo enviar el comentario." }
        }
    }

    fun cerrar(calificacion: Int, comentarioFinal: String = "") {
        val actual = ticket.value?.ticket ?: return
        if (usuarioActual.rol != RolesUsuario.CLIENTE || actual.estado != EstadoTicket.RESUELTO.name) return
        if (calificacion !in 1..5) {
            _error.value = "Selecciona una calificación de 1 a 5 estrellas."
            return
        }
        viewModelScope.launch {
            _error.value = null
            runCatching {
                if (comentarioFinal.isNotBlank()) {
                    repository.agregarComentario(ticketId, usuarioActual, comentarioFinal.trim())
                }
                repository.cerrarConCalificacion(ticketId, calificacion)
            }.onFailure { _error.value = "No se pudo cerrar el ticket. Inténtalo nuevamente." }
        }
    }

    fun reabrir() {
        val actual = ticket.value?.ticket ?: return
        if (usuarioActual.rol != RolesUsuario.CLIENTE || actual.estado != EstadoTicket.RESUELTO.name) return
        viewModelScope.launch {
            _error.value = null
            runCatching { repository.reabrir(ticketId) }
                .onFailure { _error.value = "No se pudo reabrir el ticket." }
        }
    }

    fun cambiarEstado(estado: EstadoTicket) {
        val actual = ticket.value?.ticket ?: return
        if (usuarioActual.rol != RolesUsuario.ADMIN || estado == EstadoTicket.CERRADO) return
        if (actual.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch {
            _error.value = null
            runCatching { repository.cambiarEstado(ticketId, estado) }
                .onFailure { _error.value = "No se pudo actualizar el estado del ticket." }
        }
    }
}

class TicketDetalleViewModelFactory(
    private val ticketId: Int,
    private val usuario: Usuario,
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketDetalleViewModel::class.java)) {
            return TicketDetalleViewModel(ticketId, usuario, repository) as T
        }
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
