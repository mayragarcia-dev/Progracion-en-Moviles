package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.entity.CategoriaEntity
import com.tecsup.ayudacampus.data.local.entity.ComentarioEntity
import com.tecsup.ayudacampus.data.local.entity.TicketEntity
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import kotlinx.coroutines.flow.Flow

interface AyudaRepository {
    fun observarCategoriasActivas(): Flow<List<CategoriaEntity>>
    fun observarTicketsCliente(clienteId: Int): Flow<List<TicketResumen>>
    fun observarTicketsSoporte(): Flow<List<TicketResumen>>
    fun observarDetalleSoporte(ticketId: Int): Flow<TicketResumen?>
    fun observarDetalleCliente(clienteId: Int, ticketId: Int): Flow<TicketResumen?>
    fun observarComentarios(ticketId: Int): Flow<List<ComentarioEntity>>
    fun observarResumenPanel(): Flow<ResumenPanel>

    suspend fun buscarCategoria(categoriaId: Int): CategoriaEntity?
    suspend fun buscarTicket(ticketId: Int): TicketEntity?
    suspend fun crearTicket(ticket: TicketEntity): Long
    suspend fun actualizarTicket(ticket: TicketEntity)
    suspend fun agregarComentario(comentario: ComentarioEntity): Long
}
