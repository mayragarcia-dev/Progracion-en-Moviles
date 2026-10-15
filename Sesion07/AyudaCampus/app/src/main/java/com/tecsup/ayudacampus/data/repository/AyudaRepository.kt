package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import kotlinx.coroutines.flow.Flow

interface AyudaRepository {
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

    suspend fun buscarCategoria(categoriaId: Int): Categoria?
    suspend fun buscarTicket(ticketId: Int): Ticket?
    suspend fun crearTicket(ticket: Ticket): Long
    suspend fun actualizarTicket(ticket: Ticket)
    suspend fun agregarComentario(comentario: Comentario): Long
}
