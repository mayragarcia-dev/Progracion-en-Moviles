package com.tecsup.ayudacampus.ui.navigation

object Rutas {
    const val Splash = "splash"
    const val Login = "login"
    const val Perfil = "perfil"
    const val Ajustes = "ajustes"
    const val MisTickets = "mis_tickets"
    const val Panel = "panel"
    const val BandejaSoporte = "bandeja_soporte"
    const val NuevoTicket = "nuevo_ticket"
    const val DetalleTicket = "detalle_ticket/{ticketId}"
    const val AtenderTicket = "atender_ticket/{ticketId}"
    const val Categorias = "categorias"
    const val FormCategoria = "categoria_form/{categoriaId}"

    fun detalleTicket(ticketId: Int): String = "detalle_ticket/$ticketId"
    fun atenderTicket(ticketId: Int): String = "atender_ticket/$ticketId"
    fun formCategoria(categoriaId: Int = 0): String = "categoria_form/$categoriaId"
}
