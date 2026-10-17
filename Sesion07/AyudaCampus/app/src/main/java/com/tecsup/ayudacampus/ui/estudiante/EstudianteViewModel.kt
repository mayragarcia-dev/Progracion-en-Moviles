package com.tecsup.ayudacampus.ui.estudiante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.EstadosTicket
import com.tecsup.ayudacampus.domain.model.PrioridadesTicket
import com.tecsup.ayudacampus.domain.model.RolesUsuario
import com.tecsup.ayudacampus.domain.model.UsuarioActual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EstudianteViewModel(
    private val repository: AyudaRepository,
    private val estudiante: UsuarioActual
) : ViewModel() {
    private val _uiState = MutableStateFlow(EstudianteUiState())
    val uiState: StateFlow<EstudianteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observarTicketsCliente(estudiante.id).collect { tickets ->
                _uiState.update { it.copy(tickets = tickets) }
            }
        }
        viewModelScope.launch {
            repository.observarCategoriasActivas().collect { categorias ->
                _uiState.update { it.copy(categoriasActivas = categorias) }
            }
        }
    }

    fun observarDetalle(ticketId: Int) = repository.observarDetalleCliente(estudiante.id, ticketId)

    fun observarComentarios(ticketId: Int) = combine(
        repository.observarDetalleCliente(estudiante.id, ticketId),
        repository.observarComentarios(ticketId)
    ) { detalle, comentarios ->
        if (detalle == null) emptyList() else comentarios
    }

    fun crearTicket(titulo: String, descripcion: String, ubicacion: String, categoriaId: Int) {
        ejecutar {
            exigirRolEstudiante()
            val tituloLimpio = titulo.trim()
            val descripcionLimpia = descripcion.trim()
            require(tituloLimpio.length >= 5) { "El título debe tener al menos 5 caracteres." }
            require(descripcionLimpia.length >= 10) { "La descripción debe tener al menos 10 caracteres." }
            val categoria = repository.buscarCategoria(categoriaId)
            require(categoria?.activa == true) { "Selecciona una categoría activa." }

            repository.crearTicket(
                Ticket(
                    clienteId = estudiante.id,
                    categoriaId = categoriaId,
                    titulo = tituloLimpio,
                    descripcion = descripcionLimpia,
                    ubicacion = ubicacion.trim(),
                    prioridad = PrioridadesTicket.MEDIA,
                    estado = EstadosTicket.ABIERTO,
                    fecha = System.currentTimeMillis()
                )
            )
            "Ticket creado correctamente."
        }
    }

    fun cerrarTicket(ticketId: Int, calificacion: Int) {
        ejecutar {
            exigirRolEstudiante()
            require(calificacion in 1..5) { "La calificación debe estar entre 1 y 5." }
            val ticket = ticketDeEstudiante(ticketId)
            require(ticket.estado == EstadosTicket.RESUELTO) { "Solo puedes cerrar un ticket resuelto." }
            repository.actualizarTicket(
                ticket.copy(
                    estado = EstadosTicket.CERRADO,
                    fechaCierre = System.currentTimeMillis(),
                    calificacion = calificacion
                )
            )
            "Ticket cerrado. Gracias por calificar la atención."
        }
    }

    fun reabrirTicket(ticketId: Int) {
        ejecutar {
            exigirRolEstudiante()
            val ticket = ticketDeEstudiante(ticketId)
            require(ticket.estado == EstadosTicket.RESUELTO) { "Solo puedes reabrir un ticket resuelto." }
            repository.actualizarTicket(
                ticket.copy(
                    estado = EstadosTicket.ABIERTO,
                    fechaCierre = null,
                    calificacion = null
                )
            )
            "Ticket reabierto."
        }
    }

    fun agregarComentario(ticketId: Int, texto: String) {
        ejecutar {
            exigirRolEstudiante()
            val ticket = ticketDeEstudiante(ticketId)
            require(ticket.estado != EstadosTicket.CERRADO) { "No se puede comentar en un ticket cerrado." }
            val textoLimpio = texto.trim()
            require(textoLimpio.isNotEmpty()) { "Escribe un comentario antes de enviarlo." }
            repository.agregarComentario(
                Comentario(
                    ticketId = ticketId,
                    autorId = estudiante.id,
                    autorNombre = estudiante.nombre,
                    autorRol = estudiante.rol,
                    texto = textoLimpio,
                    fecha = System.currentTimeMillis()
                )
            )
            "Comentario enviado."
        }
    }

    fun limpiarMensaje() {
        _uiState.update { it.copy(mensaje = null) }
    }

    private suspend fun ticketDeEstudiante(ticketId: Int): Ticket {
        val ticket = repository.buscarTicket(ticketId) ?: error("No se encontró el ticket.")
        require(ticket.clienteId == estudiante.id) { "No tienes permiso para acceder a este ticket." }
        return ticket
    }

    private fun exigirRolEstudiante() {
        require(estudiante.rol == RolesUsuario.CLIENTE) { "Esta acción es solo para el perfil Estudiante." }
    }

    private fun ejecutar(accion: suspend () -> String) {
        viewModelScope.launch {
            val mensaje = runCatching { accion() }.getOrElse {
                it.message ?: "No se pudo completar la acción."
            }
            _uiState.update { it.copy(mensaje = mensaje) }
        }
    }
}
