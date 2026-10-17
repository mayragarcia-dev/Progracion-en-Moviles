package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.model.ConteoCategoria
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query(
        """
        SELECT t.id, t.titulo, t.descripcion, t.ubicacion, t.prioridad, t.estado,
               t.fecha, t.fechaCierre, t.calificacion,
               u.nombre AS clienteNombre, c.nombre AS categoriaNombre,
               c.icono AS categoriaIcono,
               (SELECT COUNT(*) FROM comentarios co WHERE co.ticketId = t.id) AS comentariosCount
        FROM tickets t
        INNER JOIN usuarios u ON u.id = t.clienteId
        INNER JOIN categorias c ON c.id = t.categoriaId
        WHERE t.clienteId = :clienteId
        ORDER BY t.fecha DESC
        """
    )
    fun observarResumenPorCliente(clienteId: Int): Flow<List<TicketResumen>>

    @Query(
        """
        SELECT t.id, t.titulo, t.descripcion, t.ubicacion, t.prioridad, t.estado,
               t.fecha, t.fechaCierre, t.calificacion,
               u.nombre AS clienteNombre, c.nombre AS categoriaNombre,
               c.icono AS categoriaIcono,
               (SELECT COUNT(*) FROM comentarios co WHERE co.ticketId = t.id) AS comentariosCount
        FROM tickets t
        INNER JOIN usuarios u ON u.id = t.clienteId
        INNER JOIN categorias c ON c.id = t.categoriaId
        ORDER BY CASE t.prioridad WHEN 'ALTA' THEN 0 WHEN 'MEDIA' THEN 1 ELSE 2 END, t.fecha DESC
        """
    )
    fun observarResumenTodos(): Flow<List<TicketResumen>>

    @Query(
        """
        SELECT t.id, t.titulo, t.descripcion, t.ubicacion, t.prioridad, t.estado,
               t.fecha, t.fechaCierre, t.calificacion,
               u.nombre AS clienteNombre, c.nombre AS categoriaNombre,
               c.icono AS categoriaIcono,
               (SELECT COUNT(*) FROM comentarios co WHERE co.ticketId = t.id) AS comentariosCount
        FROM tickets t
        INNER JOIN usuarios u ON u.id = t.clienteId
        INNER JOIN categorias c ON c.id = t.categoriaId
        WHERE t.id = :ticketId
        LIMIT 1
        """
    )
    fun observarDetalle(ticketId: Int): Flow<TicketResumen?>

    @Query(
        """
        SELECT t.id, t.titulo, t.descripcion, t.ubicacion, t.prioridad, t.estado,
               t.fecha, t.fechaCierre, t.calificacion,
               u.nombre AS clienteNombre, c.nombre AS categoriaNombre,
               c.icono AS categoriaIcono,
               (SELECT COUNT(*) FROM comentarios co WHERE co.ticketId = t.id) AS comentariosCount
        FROM tickets t
        INNER JOIN usuarios u ON u.id = t.clienteId
        INNER JOIN categorias c ON c.id = t.categoriaId
        WHERE t.id = :ticketId AND t.clienteId = :clienteId
        LIMIT 1
        """
    )
    fun observarDetalleDeCliente(clienteId: Int, ticketId: Int): Flow<TicketResumen?>

    @Query(
        """
        SELECT COUNT(*) AS total,
               COALESCE(SUM(CASE WHEN estado = 'ABIERTO' THEN 1 ELSE 0 END), 0) AS abiertos,
               COALESCE(SUM(CASE WHEN estado = 'EN_PROCESO' THEN 1 ELSE 0 END), 0) AS enProceso,
               COALESCE(SUM(CASE WHEN estado = 'RESUELTO' THEN 1 ELSE 0 END), 0) AS resueltos,
               COALESCE(SUM(CASE WHEN estado = 'CERRADO' THEN 1 ELSE 0 END), 0) AS cerrados
        FROM tickets
        """
    )
    fun observarResumenPanel(): Flow<ResumenPanel>

    @Transaction
    @Query("SELECT * FROM tickets WHERE clienteId = :clienteId ORDER BY fecha DESC")
    fun observarPorCliente(clienteId: Int): Flow<List<TicketConDetalle>>

    @Transaction
    @Query("SELECT * FROM tickets ORDER BY CASE prioridad WHEN 'ALTA' THEN 0 WHEN 'MEDIA' THEN 1 ELSE 2 END, fecha DESC")
    fun observarTodos(): Flow<List<TicketConDetalle>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE id = :id LIMIT 1")
    fun observarUno(id: Int): Flow<TicketConDetalle?>

    @Query("SELECT COUNT(*) FROM tickets WHERE estado = :estado")
    fun contarPorEstado(estado: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tickets WHERE categoriaId = :categoriaId")
    suspend fun contarPorCategoriaId(categoriaId: Int): Int

    @Query(
        """
        SELECT c.id AS categoriaId, c.nombre AS nombre, COUNT(t.id) AS cantidad
        FROM categorias c
        LEFT JOIN tickets t ON t.categoriaId = c.id
        GROUP BY c.id, c.nombre
        ORDER BY c.nombre
        """
    )
    fun contarPorCategoria(): Flow<List<ConteoCategoria>>

    @Query("SELECT COUNT(*) FROM tickets WHERE prioridad = 'ALTA' AND estado = 'ABIERTO' AND fecha < :limite")
    fun contarAltasSinAtender(limite: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(ticket: Ticket): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarTicket(ticket: Ticket): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarComentario(comentario: Comentario): Long

    @Update
    suspend fun actualizar(ticket: Ticket)

    @Query("UPDATE tickets SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, estado: String)

    @Query("UPDATE tickets SET prioridad = :prioridad WHERE id = :id")
    suspend fun actualizarPrioridad(id: Int, prioridad: String)

    @Query("UPDATE tickets SET estado = 'CERRADO', calificacion = :calificacion, fechaCierre = :fechaCierre WHERE id = :id")
    suspend fun cerrar(id: Int, calificacion: Int, fechaCierre: Long)

    @Query("UPDATE tickets SET estado = 'ABIERTO', calificacion = NULL, fechaCierre = NULL WHERE id = :id")
    suspend fun reabrir(id: Int)

    @Query("SELECT * FROM tickets WHERE id = :ticketId LIMIT 1")
    suspend fun buscarPorId(ticketId: Int): Ticket?
}
