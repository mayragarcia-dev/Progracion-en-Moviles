package com.tecsup.ayudacampus.ui.soporte

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.ComentarioEntity
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadosTicket
import com.tecsup.ayudacampus.domain.model.PrioridadesTicket
import com.tecsup.ayudacampus.domain.model.RolesUsuario
import com.tecsup.ayudacampus.domain.model.UsuarioActual
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoporteViewModel(
    private val repository: AyudaRepository,
    private val soporte: UsuarioActual
) : ViewModel() {
    private val _uiState = MutableStateFlow(SoporteUiState())
    val uiState: StateFlow<SoporteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observarTicketsSoporte().collect { tickets ->
                _uiState.update { it.copy(tickets = tickets) }
            }
        }
        viewModelScope.launch {
            repository.observarResumenPanel().collect { resumen ->
                _uiState.update { it.copy(resumen = resumen) }
            }
        }
        viewModelScope.launch {
            combine(repository.observarTicketsSoporte(), reloj()) { tickets, ahora ->
                val limite = ahora - UNA_HORA_MILLIS
                tickets.count {
                    it.prioridad == PrioridadesTicket.ALTA &&
                        it.estado == EstadosTicket.ABIERTO &&
                        it.fecha < limite
                }
            }.collect { cantidad ->
                _uiState.update { it.copy(alertasAltaAntiguedad = cantidad) }
            }
        }
    }

    fun observarDetalle(ticketId: Int) = repository.observarDetalleSoporte(ticketId)

    fun observarComentarios(ticketId: Int) = repository.observarComentarios(ticketId)

    fun cambiarEstado(ticketId: Int, nuevoEstado: String) {
        ejecutar {
            exigirRolSoporte()
            require(nuevoEstado in EstadosTicket.editablesPorSoporte) {
                "Soporte solo puede usar ABIERTO, EN_PROCESO o RESUELTO."
            }
            val ticket = repository.buscarTicket(ticketId) ?: error("No se encontró el ticket.")
            require(ticket.estado != EstadosTicket.CERRADO) { "Un ticket cerrado no puede ser modificado por Soporte." }
            repository.actualizarTicket(ticket.copy(estado = nuevoEstado))
            "Estado actualizado."
        }
    }

    fun cambiarPrioridad(ticketId: Int, nuevaPrioridad: String) {
        ejecutar {
            exigirRolSoporte()
            require(nuevaPrioridad in PrioridadesTicket.validas) { "Selecciona una prioridad válida." }
            val ticket = repository.buscarTicket(ticketId) ?: error("No se encontró el ticket.")
            repository.actualizarTicket(ticket.copy(prioridad = nuevaPrioridad))
            "Prioridad actualizada."
        }
    }

    fun agregarComentario(ticketId: Int, texto: String) {
        ejecutar {
            exigirRolSoporte()
            val ticket = repository.buscarTicket(ticketId) ?: error("No se encontró el ticket.")
            require(ticket.estado != EstadosTicket.CERRADO) { "No se puede comentar en un ticket cerrado." }
            val textoLimpio = texto.trim()
            require(textoLimpio.isNotEmpty()) { "Escribe un comentario antes de enviarlo." }
            repository.agregarComentario(
                ComentarioEntity(
                    ticketId = ticketId,
                    autorId = soporte.id,
                    autorNombre = soporte.nombre,
                    autorRol = soporte.rol,
                    texto = textoLimpio,
                    fecha = System.currentTimeMillis()
                )
            )
            "Respuesta enviada."
        }
    }

    fun limpiarMensaje() {
        _uiState.update { it.copy(mensaje = null) }
    }

    private fun exigirRolSoporte() {
        require(soporte.rol == RolesUsuario.ADMIN) { "Esta acción es solo para el perfil Soporte TI." }
    }

    private fun ejecutar(accion: suspend () -> String) {
        viewModelScope.launch {
            val mensaje = runCatching { accion() }.getOrElse {
                it.message ?: "No se pudo completar la acción."
            }
            _uiState.update { it.copy(mensaje = mensaje) }
        }
    }

    private fun reloj() = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(UN_MINUTO_MILLIS)
        }
    }

    private companion object {
        const val UNA_HORA_MILLIS = 60 * 60 * 1000L
        const val UN_MINUTO_MILLIS = 60 * 1000L
    }
}
