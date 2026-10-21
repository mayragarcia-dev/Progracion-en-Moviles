package com.tecsup.ayudacampus.data.local

import androidx.room.withTransaction
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario

object DatosIniciales {
    suspend fun sembrar(db: AyudaDatabase) = db.withTransaction {
        val usuarioDao = db.usuarioDao()
        if (usuarioDao.contar() != 0) return@withTransaction

        db.categoriaDao().insertarTodas(
            listOf(
                Categoria(nombre = "Hardware", icono = "pc"),
                Categoria(nombre = "Software", icono = "code"),
                Categoria(nombre = "Red / Wi-Fi", icono = "wifi"),
                Categoria(nombre = "Cuenta", icono = "key"),
                Categoria(nombre = "Impresión", icono = "printer")
            )
        )
        usuarioDao.insertarTodos(
            listOf(
                Usuario(nombre = "Mayra", correo = "mayra@campus.test", clave = "1234", rol = "CLIENTE"),
                Usuario(nombre = "Soporte TI", correo = "soporte@campus.test", clave = "admin", rol = "ADMIN")
            )
        )

        val clienteId = requireNotNull(usuarioDao.login("mayra@campus.test", "1234")).id
        val soporteId = requireNotNull(usuarioDao.login("soporte@campus.test", "admin")).id
        val categorias = db.categoriaDao().obtenerTodas().associateBy { it.nombre }
        val ahora = System.currentTimeMillis()
        val hora = 3_600_000L
        val tickets = listOf(
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Red / Wi-Fi").id,
                titulo = "No conecta al Wi-Fi", descripcion = "Mi laptop no encuentra la red del campus.", ubicacion = "Biblioteca, piso 2", prioridad = "ALTA", estado = "ABIERTO", fecha = ahora - 3 * hora),
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Hardware").id,
                titulo = "Equipo no enciende", descripcion = "La computadora del laboratorio no inicia.", ubicacion = "Laboratorio 3", prioridad = "ALTA", estado = "EN_PROCESO", fecha = ahora - 8 * hora),
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Cuenta").id,
                titulo = "Cuenta bloqueada", descripcion = "No puedo ingresar al portal académico.", ubicacion = "Remoto", prioridad = "MEDIA", estado = "RESUELTO", fecha = ahora - 12 * hora, fechaCierre = ahora - 2 * hora),
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Software").id,
                titulo = "Instalar herramienta de programación", descripcion = "Necesito el IDE para el curso.", ubicacion = "Laboratorio 1", prioridad = "BAJA", estado = "CERRADO", fecha = ahora - 30 * hora, fechaCierre = ahora - 20 * hora, calificacion = 5),
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Impresión").id,
                titulo = "Impresora sin conexión", descripcion = "La impresora del pabellón no aparece en la red.", ubicacion = "Pabellón A", prioridad = "MEDIA", estado = "ABIERTO", fecha = ahora - 20 * 60_000L),
            Ticket(clienteId = clienteId, categoriaId = categorias.getValue("Red / Wi-Fi").id,
                titulo = "Conexión lenta", descripcion = "La conexión se interrumpe durante las clases.", ubicacion = "Aula 204", prioridad = "BAJA", estado = "EN_PROCESO", fecha = ahora - 5 * hora)
        )
        val ticketIds = tickets.map { db.ticketDao().insertarTicket(it).toInt() }

        listOf(
            Comentario(ticketId = ticketIds[0], autorId = clienteId, autorNombre = "Mayra", autorRol = "CLIENTE", texto = "El problema comenzó esta mañana.", fecha = ahora - 2 * hora),
            Comentario(ticketId = ticketIds[0], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "Estamos revisando el punto de acceso de la biblioteca.", fecha = ahora - 90 * 60_000L),
            Comentario(ticketId = ticketIds[1], autorId = soporteId, autorNombre = "Soporte TI", autorRol = "ADMIN", texto = "Se solicitó una revisión del equipo en el laboratorio.", fecha = ahora - 4 * hora),
            Comentario(ticketId = ticketIds[3], autorId = clienteId, autorNombre = "Mayra", autorRol = "CLIENTE", texto = "Gracias por la ayuda, quedó instalado.", fecha = ahora - 21 * hora)
        ).forEach { db.ticketDao().insertarComentario(it) }
    }
}
