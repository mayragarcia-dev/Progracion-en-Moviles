package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.Prioridad
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtenderViewModel(
    private val ticketId: Int,
    val usuarioActual: Usuario,
    private val repo: AyudaRepository
) : ViewModel() {
    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    private val _prioridad = MutableStateFlow<Prioridad?>(null)
    private val _mensaje = MutableStateFlow<String?>(null)
    val estado = _estado.asStateFlow()
    val prioridad = _prioridad.asStateFlow()
    val mensaje = _mensaje.asStateFlow()

    fun cambiarEstado(estado: EstadoTicket) {
        if (estado !in listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO)) return
        if (ticket.value?.ticket?.estado == EstadoTicket.CERRADO.name) return
        _estado.value = estado
    }
    fun cambiarPrioridad(p: Prioridad) {
        if (ticket.value?.ticket?.estado != EstadoTicket.CERRADO.name) _prioridad.value = p
    }
    fun responder(texto: String) {
        val contenido = texto.trim()
        if (contenido.isEmpty() || ticket.value?.ticket?.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch {
            _mensaje.value = null
            runCatching { repo.agregarComentario(ticketId, usuarioActual, contenido) }
                .onFailure { _mensaje.value = "No se pudo enviar la respuesta." }
        }
    }
    fun guardar() {
        val actual = ticket.value ?: return
        if (usuarioActual.rol != Rol.ADMIN.name || actual.ticket.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch {
            _mensaje.value = null
            runCatching {
                _estado.value?.let { repo.cambiarEstado(ticketId, it) }
                _prioridad.value?.let { repo.cambiarPrioridad(ticketId, it) }
                _estado.value = null; _prioridad.value = null
            }.onFailure { _mensaje.value = "No se pudieron guardar los cambios." }
        }
    }
}

class AtenderViewModelFactory(private val id: Int, private val usuario: Usuario, private val repo: AyudaRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AtenderViewModel::class.java)) return AtenderViewModel(id, usuario, repo) as T
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
