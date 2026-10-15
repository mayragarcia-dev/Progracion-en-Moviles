package com.tecsup.ayudacampus.domain.model

object RolesUsuario {
    const val CLIENTE = "CLIENTE"
    const val ADMIN = "ADMIN"
}

object EstadosTicket {
    const val ABIERTO = "ABIERTO"
    const val EN_PROCESO = "EN_PROCESO"
    const val RESUELTO = "RESUELTO"
    const val CERRADO = "CERRADO"

    val editablesPorSoporte = setOf(ABIERTO, EN_PROCESO, RESUELTO)
}

object PrioridadesTicket {
    const val BAJA = "BAJA"
    const val MEDIA = "MEDIA"
    const val ALTA = "ALTA"
    val validas = setOf(BAJA, MEDIA, ALTA)
}

data class UsuarioActual(
    val id: Int,
    val nombre: String,
    val rol: String
)
