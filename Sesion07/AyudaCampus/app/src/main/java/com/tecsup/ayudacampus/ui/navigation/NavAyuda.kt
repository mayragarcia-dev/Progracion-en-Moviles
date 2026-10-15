package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.NuevoTicketScreen
import com.tecsup.ayudacampus.ui.cliente.DetalleTicketScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.soporte.BandejaScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel
import com.tecsup.ayudacampus.viewmodel.BandejaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModelFactory
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModelFactory
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModelFactory
import kotlinx.coroutines.flow.first

@Composable
fun NavAyuda(
    repository: AyudaRepository,
    preferencias: PreferenciasStore
) {
    val navController = rememberNavController()
    val factory = AyudaViewModelFactory(repository, preferencias)
    val sesionViewModel: SesionViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = Rutas.Splash) {
        composable(Rutas.Splash) {
            SplashScreen()
            LaunchedEffect(Unit) {
                val perfil = preferencias.perfilActivo.first()
                val destino = when (perfil) {
                    Rol.CLIENTE.name -> Rutas.MisTickets
                    Rol.ADMIN.name -> Rutas.Panel
                    else -> Rutas.Login
                }
                navController.navigate(destino) {
                    popUpTo(Rutas.Splash) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        composable(Rutas.Login) {
            LoginScreen(vm = sesionViewModel) { rol ->
                val destino = if (rol == Rol.CLIENTE) Rutas.MisTickets else Rutas.Panel
                navController.navigate(destino) {
                    popUpTo(Rutas.Login) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        composable(Rutas.MisTickets) {
            val usuario by produceState<Usuario?>(initialValue = null) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            if (usuario != null) {
                val usuarioActual = requireNotNull(usuario)
                val ticketsViewModel: MisTicketsViewModel = viewModel(
                    key = "mis-tickets-${usuarioActual.id}",
                    factory = MisTicketsViewModelFactory(usuarioActual, repository)
                )
                MisTicketsScreen(
                    vm = ticketsViewModel,
                    onTicketClick = { ticketId -> navController.navigate(Rutas.detalleTicket(ticketId)) },
                    onNuevo = { navController.navigate(Rutas.NuevoTicket) }
                )
            } else {
                PantallaDestino("Cargando tickets", "Estudiante")
            }
        }
        composable(Rutas.Panel) {
            val usuario by produceState<Usuario?>(initialValue = null) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            if (usuario?.rol == Rol.ADMIN.name) {
                val bandejaViewModel: BandejaViewModel = viewModel(factory = BandejaViewModelFactory(repository))
                BandejaScreen(vm = bandejaViewModel) { ticketId -> navController.navigate(Rutas.detalleTicket(ticketId)) }
            } else {
                PantallaDestino("Preparando bandeja", "Soporte TI")
            }
        }
        composable(Rutas.NuevoTicket) {
            val usuario by produceState<Usuario?>(initialValue = null) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            if (usuario != null) {
                val usuarioActual = requireNotNull(usuario)
                val nuevoTicketViewModel: NuevoTicketViewModel = viewModel(
                    key = "nuevo-ticket-${usuarioActual.id}",
                    factory = NuevoTicketViewModelFactory(usuarioActual, repository)
                )
                NuevoTicketScreen(
                    vm = nuevoTicketViewModel,
                    onTicketCreado = { navController.popBackStack(Rutas.MisTickets, inclusive = false) },
                    onTicketsClick = { navController.popBackStack(Rutas.MisTickets, inclusive = false) }
                )
            } else {
                PantallaDestino("Preparando formulario", "Estudiante")
            }
        }
        composable(
            route = Rutas.DetalleTicket,
            arguments = listOf(navArgument("ticketId") { type = NavType.IntType })
        ) { entrada ->
            val ticketId = entrada.arguments?.getInt("ticketId") ?: return@composable
            val usuario by produceState<Usuario?>(initialValue = null, ticketId) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            if (usuario != null) {
                val usuarioActual = requireNotNull(usuario)
                val detalleViewModel: TicketDetalleViewModel = viewModel(
                    key = "detalle-$ticketId-${usuarioActual.id}",
                    factory = TicketDetalleViewModelFactory(ticketId, usuarioActual, repository)
                )
                DetalleTicketScreen(
                    vm = detalleViewModel,
                    onTicketsClick = {
                        navController.popBackStack(
                            if (usuarioActual.rol == Rol.ADMIN.name) Rutas.Panel else Rutas.MisTickets,
                            inclusive = false
                        )
                    },
                    onNuevoClick = {
                        if (usuarioActual.rol == Rol.CLIENTE.name) navController.navigate(Rutas.NuevoTicket)
                    }
                )
            } else {
                PantallaDestino("Cargando detalle", "Ticket")
            }
        }
    }
}

@Composable
private fun PantallaDestino(titulo: String, perfil: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall)
        Text("Sesión iniciada: $perfil", style = MaterialTheme.typography.bodyMedium)
    }
}
