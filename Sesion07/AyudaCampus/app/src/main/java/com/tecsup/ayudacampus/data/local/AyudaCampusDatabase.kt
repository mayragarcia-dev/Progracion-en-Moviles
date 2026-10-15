package com.tecsup.ayudacampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.dao.ComentarioDao
import com.tecsup.ayudacampus.data.local.dao.TicketDao
import com.tecsup.ayudacampus.data.local.dao.UsuarioDao
import com.tecsup.ayudacampus.data.local.entity.CategoriaEntity
import com.tecsup.ayudacampus.data.local.entity.ComentarioEntity
import com.tecsup.ayudacampus.data.local.entity.TicketEntity
import com.tecsup.ayudacampus.data.local.entity.UsuarioEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [UsuarioEntity::class, CategoriaEntity::class, TicketEntity::class, ComentarioEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AyudaCampusDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun ticketDao(): TicketDao
    abstract fun comentarioDao(): ComentarioDao

    companion object {
        @Volatile
        private var INSTANCE: AyudaCampusDatabase? = null

        fun obtenerInstancia(context: Context): AyudaCampusDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: construir(context.applicationContext).also { INSTANCE = it }
            }

        private fun construir(context: Context): AyudaCampusDatabase {
            lateinit var database: AyudaCampusDatabase
            database = Room.databaseBuilder(
                context,
                AyudaCampusDatabase::class.java,
                "ayudacampus.db"
            ).addCallback(object : Callback() {
                override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        database.withTransaction { database.cargarDatosIniciales() }
                    }
                }
            }).build()
            return database
        }

        private suspend fun AyudaCampusDatabase.cargarDatosIniciales() {
            val usuarioDao = usuarioDao()
            val categoriaDao = categoriaDao()
            val ticketDao = ticketDao()
            val comentarioDao = comentarioDao()

            val clienteId = usuarioDao.buscarPorCorreo("juan@campus.test")?.id
                ?: usuarioDao.insertar(
                    UsuarioEntity(nombre = "Juan Pérez", correo = "juan@campus.test", clave = "1234", rol = "CLIENTE")
                ).toInt()
            val soporteId = usuarioDao.buscarPorCorreo("soporte@campus.test")?.id
                ?: usuarioDao.insertar(
                    UsuarioEntity(nombre = "Soporte TI", correo = "soporte@campus.test", clave = "admin", rol = "ADMIN")
                ).toInt()

            val nombres = listOf(
                "Hardware" to "pc",
                "Software" to "code",
                "Red / Wi-Fi" to "wifi",
                "Cuenta" to "key",
                "Impresión" to "printer"
            )
            nombres.forEach { (nombre, icono) ->
                if (categoriaDao.obtenerTodas().none { it.nombre == nombre }) {
                    categoriaDao.insertar(CategoriaEntity(nombre = nombre, icono = icono))
                }
            }
            val categorias = categoriaDao.obtenerTodas().associateBy { it.nombre }
            val ahora = System.currentTimeMillis()
            val ejemplos = listOf(
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Red / Wi-Fi").id,
                    titulo = "No conecta al Wi-Fi", descripcion = "Mi laptop no encuentra la red del campus.", ubicacion = "Biblioteca, piso 2", prioridad = "ALTA", estado = "ABIERTO", fecha = ahora - 86_400_000L),
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Hardware").id,
                    titulo = "Equipo no enciende", descripcion = "La computadora del laboratorio no inicia.", ubicacion = "Laboratorio 3", prioridad = "ALTA", estado = "EN_PROCESO", fecha = ahora - 172_800_000L),
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Cuenta").id,
                    titulo = "Cuenta bloqueada", descripcion = "No puedo ingresar al portal académico.", ubicacion = "Remoto", prioridad = "MEDIA", estado = "RESUELTO", fecha = ahora - 259_200_000L, fechaCierre = ahora - 172_800_000L),
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Software").id,
                    titulo = "Instalar herramienta de programación", descripcion = "Necesito el IDE para el curso.", ubicacion = "Laboratorio 1", prioridad = "BAJA", estado = "CERRADO", fecha = ahora - 345_600_000L, fechaCierre = ahora - 259_200_000L, calificacion = 5),
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Impresión").id,
                    titulo = "Impresora sin conexión", descripcion = "La impresora del pabellón no aparece en la red.", ubicacion = "Pabellón A", prioridad = "MEDIA", estado = "ABIERTO", fecha = ahora - 43_200_000L),
                TicketEntity(clienteId = clienteId, categoriaId = categorias.getValue("Red / Wi-Fi").id,
                    titulo = "Conexión lenta", descripcion = "La conexión se interrumpe durante las clases.", ubicacion = "Aula 204", prioridad = "BAJA", estado = "EN_PROCESO", fecha = ahora - 21_600_000L)
            )
            val ticketIds = ejemplos.map { ticketDao.insertar(it).toInt() }
            val comentarios = listOf(
                ComentarioEntity(ticketId = ticketIds[0], autorId = clienteId, autorNombre = "Juan Pérez", autorRol = "CLIENTE", texto = "El problema comenzó esta mañana.", fecha = ahora - 80_000_000L),
                ComentarioEntity(ticketId = ticketIds[0], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "Estamos revisando el punto de acceso de la biblioteca.", fecha = ahora - 79_000_000L),
                ComentarioEntity(ticketId = ticketIds[1], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "Se solicitó una revisión del equipo en el laboratorio.", fecha = ahora - 160_000_000L),
                ComentarioEntity(ticketId = ticketIds[2], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "La cuenta fue desbloqueada. Prueba nuevamente.", fecha = ahora - 170_000_000L),
                ComentarioEntity(ticketId = ticketIds[3], autorId = clienteId, autorNombre = "Juan Pérez", autorRol = "CLIENTE", texto = "Gracias por la ayuda, quedó instalado.", fecha = ahora - 260_000_000L),
                ComentarioEntity(ticketId = ticketIds[5], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "Estamos midiendo la señal en el aula.", fecha = ahora - 20_000_000L)
            )
            comentarios.forEach { comentarioDao.insertar(it) }
        }
    }
}
