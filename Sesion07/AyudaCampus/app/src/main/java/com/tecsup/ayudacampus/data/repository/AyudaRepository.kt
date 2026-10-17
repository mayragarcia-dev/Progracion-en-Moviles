package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.model.ConteoCategoria
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import com.tecsup.ayudacampus.domain.model.Prioridad
import kotlinx.coroutines.flow.Flow

interface AyudaRepository {
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun buscarUsuario(usuarioId: Int): Usuario?
    suspend fun buscarUsuarioPorRol(rol: String): Usuario?

    fun observarCategorias(): Flow<List<Categoria>>
    fun observarCategoriasActivas(): Flow<List<Categoria>>
    suspend fun guardarCategoria(categoria: Categoria)
    suspend fun eliminarCategoria(categoria: Categoria)
    suspend fun cambiarActiva(id: Int, activa: Boolean)
    fun observarTicketsCliente(clienteId: Int): Flow<List<TicketResumen>>
    fun observarTicketsSoporte(): Flow<List<TicketResumen>>
    fun observarDetalleSoporte(ticketId: Int): Flow<TicketResumen?>
    fun observarDetalleCliente(clienteId: Int, ticketId: Int): Flow<TicketResumen?>
    fun observarComentarios(ticketId: Int): Flow<List<Comentario>>
    fun observarResumenPanel(): Flow<ResumenPanel>
    fun observarPromedioResolucion(): Flow<Double?>

    suspend fun buscarCategoria(categoriaId: Int): Categoria?
    suspend fun contarTicketsCategoria(categoriaId: Int): Int
    suspend fun buscarTicket(ticketId: Int): Ticket?
    suspend fun crearTicket(ticket: Ticket): Long
    suspend fun crearTicket(
        clienteId: Int,
        categoriaId: Int,
        titulo: String,
        descripcion: String,
        ubicacion: String,
        prioridad: Prioridad
    ): Long
    suspend fun actualizarTicket(ticket: Ticket)
    suspend fun agregarComentario(comentario: Comentario): Long
    suspend fun agregarComentario(ticketId: Int, autor: Usuario, texto: String): Long
    suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket)
    suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad)
    suspend fun cerrarConCalificacion(ticketId: Int, calificacion: Int)
    suspend fun reabrir(ticketId: Int)

    fun observarTicketsClienteDetallados(clienteId: Int): Flow<List<TicketConDetalle>>
    fun observarTodosLosTickets(): Flow<List<TicketConDetalle>>
    fun observarTicket(ticketId: Int): Flow<TicketConDetalle?>
    fun observarCantidadPorEstado(estado: EstadoTicket): Flow<Int>
    fun observarCantidadPorCategoria(): Flow<List<ConteoCategoria>>
    fun observarAltasSinAtender(limite: Long): Flow<Int>
}
