package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tecsup.ayudacampus.data.local.entity.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {
    @Query("SELECT * FROM categorias ORDER BY nombre")
    fun observarTodas(): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM categorias WHERE activa = 1 ORDER BY nombre")
    fun observarActivas(): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM categorias ORDER BY nombre")
    suspend fun obtenerTodas(): List<CategoriaEntity>

    @Query("SELECT * FROM categorias WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): CategoriaEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(categoria: CategoriaEntity): Long

    @Update
    suspend fun actualizar(categoria: CategoriaEntity)
}
