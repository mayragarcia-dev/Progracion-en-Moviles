package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.dao.ComentarioDao
import com.tecsup.ayudacampus.data.local.dao.TicketDao
import com.tecsup.ayudacampus.data.local.dao.UsuarioDao
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

class AyudaRepositoryRoom(
    private val usuarioDao: UsuarioDao,
    private val categoriaDao: CategoriaDao,
    private val ticketDao: TicketDao,
    private val comentarioDao: ComentarioDao
) : AyudaRepository {
    override suspend fun login(correo: String, clave: String): Usuario? =
        usuarioDao.login(correo, clave)

    override suspend fun buscarUsuario(usuarioId: Int): Usuario? = usuarioDao.buscarPorId(usuarioId)

    override suspend fun buscarUsuarioPorRol(rol: String): Usuario? = usuarioDao.buscarPorRol(rol)

    override suspend fun buscarPorCorreo(correo: String): Usuario? = usuarioDao.buscarPorCorreo(correo)

    override suspend fun buscarPorRol(rol: String): Usuario? = usuarioDao.buscarPorRol(rol)

    override suspend fun registrarUsuario(usuario: Usuario): Long = usuarioDao.insertar(usuario)

    override fun observarCategorias(): Flow<List<Categoria>> = categoriaDao.observarTodas()

    override fun observarCategoriasActivas(): Flow<List<Categoria>> = categoriaDao.observarActivas()

    override suspend fun guardarCategoria(categoria: Categoria) {
        if (categoria.id == 0) categoriaDao.insertar(categoria) else categoriaDao.actualizar(categoria)
    }

    override suspend fun eliminarCategoria(categoria: Categoria) = categoriaDao.eliminar(categoria)

    override suspend fun cambiarActiva(id: Int, activa: Boolean) = categoriaDao.cambiarActiva(id, activa)

    override fun observarTicketsCliente(clienteId: Int): Flow<List<TicketResumen>> =
        ticketDao.observarResumenPorCliente(clienteId)

    override fun observarTicketsSoporte(): Flow<List<TicketResumen>> = ticketDao.observarResumenTodos()

    override fun observarDetalleSoporte(ticketId: Int): Flow<TicketResumen?> =
        ticketDao.observarDetalle(ticketId)

    override fun observarDetalleCliente(clienteId: Int, ticketId: Int): Flow<TicketResumen?> =
        ticketDao.observarDetalleDeCliente(clienteId, ticketId)

    override fun observarComentarios(ticketId: Int): Flow<List<Comentario>> =
        comentarioDao.observarPorTicket(ticketId)

    override fun observarResumenPanel(): Flow<ResumenPanel> = ticketDao.observarResumenPanel()

    override fun observarPromedioResolucion(): Flow<Double?> = ticketDao.observarPromedioResolucion()

    override suspend fun buscarCategoria(categoriaId: Int): Categoria? =
        categoriaDao.buscarPorId(categoriaId)

    override suspend fun contarTicketsCategoria(categoriaId: Int): Int =
        ticketDao.contarPorCategoriaId(categoriaId)

    override suspend fun buscarTicket(ticketId: Int): Ticket? = ticketDao.buscarPorId(ticketId)

    override suspend fun crearTicket(ticket: Ticket): Long = ticketDao.insertar(ticket)

    override suspend fun crearTicket(
        clienteId: Int,
        categoriaId: Int,
        titulo: String,
        descripcion: String,
        ubicacion: String,
        prioridad: Prioridad
    ): Long = ticketDao.insertarTicket(
        Ticket(
            clienteId = clienteId,
            categoriaId = categoriaId,
            titulo = titulo,
            descripcion = descripcion,
            ubicacion = ubicacion,
            prioridad = prioridad.name,
            estado = EstadoTicket.ABIERTO.name,
            fecha = System.currentTimeMillis()
        )
    )

    override suspend fun actualizarTicket(ticket: Ticket) = ticketDao.actualizar(ticket)

    override suspend fun agregarComentario(comentario: Comentario): Long = comentarioDao.insertar(comentario)

    override suspend fun agregarComentario(ticketId: Int, autor: Usuario, texto: String): Long =
        comentarioDao.insertar(
            Comentario(
                ticketId = ticketId,
                autorId = autor.id,
                autorNombre = autor.nombre,
                autorRol = autor.rol,
                texto = texto,
                fecha = System.currentTimeMillis()
            )
        )

    override suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket) =
        ticketDao.actualizarEstado(ticketId, estado.name)

    override suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad) =
        ticketDao.actualizarPrioridad(ticketId, prioridad.name)

    override suspend fun cerrarConCalificacion(ticketId: Int, calificacion: Int) =
        ticketDao.cerrar(ticketId, calificacion, System.currentTimeMillis())

    override suspend fun reabrir(ticketId: Int) = ticketDao.reabrir(ticketId)

    override fun observarTicketsClienteDetallados(clienteId: Int): Flow<List<TicketConDetalle>> =
        ticketDao.observarPorCliente(clienteId)

    override fun observarTodosLosTickets(): Flow<List<TicketConDetalle>> = ticketDao.observarTodos()

    override fun observarTicket(ticketId: Int): Flow<TicketConDetalle?> = ticketDao.observarUno(ticketId)

    override fun observarCantidadPorEstado(estado: EstadoTicket): Flow<Int> =
        ticketDao.contarPorEstado(estado.name)

    override fun observarCantidadPorCategoria(): Flow<List<ConteoCategoria>> = ticketDao.contarPorCategoria()

    override fun observarAltasSinAtender(limite: Long): Flow<Int> = ticketDao.contarAltasSinAtender(limite)
}
