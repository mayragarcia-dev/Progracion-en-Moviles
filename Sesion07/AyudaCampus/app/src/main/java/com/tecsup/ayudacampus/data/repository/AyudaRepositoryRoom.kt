package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.dao.ComentarioDao
import com.tecsup.ayudacampus.data.local.dao.TicketDao
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.ComentarioEntity
import com.tecsup.ayudacampus.data.local.entity.TicketEntity
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(
    private val categoriaDao: CategoriaDao,
    private val ticketDao: TicketDao,
    private val comentarioDao: ComentarioDao
) : AyudaRepository {
    override fun observarCategorias(): Flow<List<Categoria>> = categoriaDao.observarTodas()

    override fun observarCategoriasActivas(): Flow<List<Categoria>> = categoriaDao.observarActivas()

    override suspend fun guardarCategoria(categoria: Categoria) {
        if (categoria.id == 0) categoriaDao.insertar(categoria) else categoriaDao.actualizar(categoria)
    }

    override suspend fun eliminarCategoria(categoria: Categoria) = categoriaDao.eliminar(categoria)

    override suspend fun cambiarActiva(id: Int, activa: Boolean) = categoriaDao.cambiarActiva(id, activa)

    override fun observarTicketsCliente(clienteId: Int): Flow<List<TicketResumen>> =
        ticketDao.observarPorCliente(clienteId)

    override fun observarTicketsSoporte(): Flow<List<TicketResumen>> = ticketDao.observarTodos()

    override fun observarDetalleSoporte(ticketId: Int): Flow<TicketResumen?> =
        ticketDao.observarDetalle(ticketId)

    override fun observarDetalleCliente(clienteId: Int, ticketId: Int): Flow<TicketResumen?> =
        ticketDao.observarDetalleDeCliente(clienteId, ticketId)

    override fun observarComentarios(ticketId: Int): Flow<List<ComentarioEntity>> =
        comentarioDao.observarPorTicket(ticketId)

    override fun observarResumenPanel(): Flow<ResumenPanel> = ticketDao.observarResumenPanel()

    override suspend fun buscarCategoria(categoriaId: Int): Categoria? =
        categoriaDao.buscarPorId(categoriaId)

    override suspend fun buscarTicket(ticketId: Int): TicketEntity? = ticketDao.buscarPorId(ticketId)

    override suspend fun crearTicket(ticket: TicketEntity): Long = ticketDao.insertar(ticket)

    override suspend fun actualizarTicket(ticket: TicketEntity) = ticketDao.actualizar(ticket)

    override suspend fun agregarComentario(comentario: ComentarioEntity): Long = comentarioDao.insertar(comentario)
}
