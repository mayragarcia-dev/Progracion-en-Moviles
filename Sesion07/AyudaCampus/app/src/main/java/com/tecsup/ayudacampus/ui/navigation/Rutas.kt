package com.tecsup.ayudacampus.ui.navigation

object Rutas {
    const val Splash = "splash"
    const val Login = "login"
    const val MisTickets = "mis_tickets"
    const val Panel = "panel"
    const val NuevoTicket = "nuevo_ticket"
    const val DetalleTicket = "detalle_ticket/{ticketId}"

    fun detalleTicket(ticketId: Int): String = "detalle_ticket/$ticketId"
}
