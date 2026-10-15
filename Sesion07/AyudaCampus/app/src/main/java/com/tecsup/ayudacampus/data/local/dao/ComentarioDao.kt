package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tecsup.ayudacampus.data.local.entity.ComentarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioDao {
    @Query("SELECT * FROM comentarios WHERE ticketId = :ticketId ORDER BY fecha, id")
    fun observarPorTicket(ticketId: Int): Flow<List<ComentarioEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(comentario: ComentarioEntity): Long
}
