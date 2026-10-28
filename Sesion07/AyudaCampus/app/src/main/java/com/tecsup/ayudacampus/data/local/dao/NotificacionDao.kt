package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tecsup.ayudacampus.data.local.entity.Notificacion
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificacionDao {
    @Query("SELECT * FROM notificaciones ORDER BY fecha DESC")
    fun observarTodas(): Flow<List<Notificacion>>

    @Insert
    suspend fun insertar(notificacion: Notificacion): Long

    @Query("UPDATE notificaciones SET leida = 1")
    suspend fun marcarTodasLeidas()
}
