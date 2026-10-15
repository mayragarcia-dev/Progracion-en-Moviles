package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tecsup.ayudacampus.data.local.entity.TicketEntity
import com.tecsup.ayudacampus.data.local.model.ResumenPanel
import com.tecsup.ayudacampus.data.local.model.TicketResumen
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
        ORDER BY CASE t.prioridad WHEN 'ALTA' THEN 0 WHEN 'MEDIA' THEN 1 ELSE 2 END, t.fecha DESC
        """
    )
    fun observarPorCliente(clienteId: Int): Flow<List<TicketResumen>>

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
    fun observarTodos(): Flow<List<TicketResumen>>

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
        SELECT COUNT(*) AS total,
               COALESCE(SUM(CASE WHEN estado = 'ABIERTO' THEN 1 ELSE 0 END), 0) AS abiertos,
               COALESCE(SUM(CASE WHEN estado = 'EN_PROCESO' THEN 1 ELSE 0 END), 0) AS enProceso,
               COALESCE(SUM(CASE WHEN estado = 'RESUELTO' THEN 1 ELSE 0 END), 0) AS resueltos,
               COALESCE(SUM(CASE WHEN estado = 'CERRADO' THEN 1 ELSE 0 END), 0) AS cerrados
        FROM tickets
        """
    )
    fun observarResumenPanel(): Flow<ResumenPanel>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(ticket: TicketEntity): Long

    @Update
    suspend fun actualizar(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE id = :ticketId LIMIT 1")
    suspend fun buscarPorId(ticketId: Int): TicketEntity?
}
