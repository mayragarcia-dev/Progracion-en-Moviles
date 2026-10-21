package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.tecsup.ayudacampus.ui.cliente.PerfilScreen
import com.tecsup.ayudacampus.ui.soporte.AjustesScreen
import com.tecsup.ayudacampus.ui.soporte.BandejaScreen
import com.tecsup.ayudacampus.ui.soporte.PanelScreen
import com.tecsup.ayudacampus.ui.soporte.AtenderTicketScreen
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.ui.soporte.FormularioCategoriaScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel
import com.tecsup.ayudacampus.viewmodel.BandejaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModelFactory
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModelFactory
import com.tecsup.ayudacampus.viewmodel.PanelViewModel
import com.tecsup.ayudacampus.viewmodel.PanelViewModelFactory
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModelFactory
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel
import com.tecsup.ayudacampus.viewmodel.AtenderViewModelFactory
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun NavAyuda(
    repository: AyudaRepository,
    preferencias: PreferenciasStore
) {
    val navController = rememberNavController()
    val factory = AyudaViewModelFactory(repository, preferencias)
    val sesionViewModel: SesionViewModel = viewModel(factory = factory)
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = Rutas.Login) {
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
                    onNuevo = { navController.navigate(Rutas.NuevoTicket) },
                    onPerfil = { navController.navigate(Rutas.Perfil) }
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
                val panelViewModel: PanelViewModel = viewModel(factory = PanelViewModelFactory(repository))
                PanelScreen(
                    vm = panelViewModel,
                    onVerTickets = { navController.navigate(Rutas.BandejaSoporte) },
                    onBandejaClick = { navController.navigate(Rutas.BandejaSoporte) },
                    onAjustes = { navController.navigate(Rutas.Ajustes) },
                    onCategorias = { navController.navigate(Rutas.Categorias) }
                )
            } else {
                PantallaDestino("Preparando panel", "Soporte TI")
            }
        }
        composable(Rutas.BandejaSoporte) {
            val usuario by produceState<Usuario?>(initialValue = null) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            if (usuario?.rol == Rol.ADMIN.name) {
                val bandejaViewModel: BandejaViewModel = viewModel(factory = BandejaViewModelFactory(repository))
                BandejaScreen(
                    vm = bandejaViewModel,
                    onTicketClick = { ticketId -> navController.navigate(Rutas.atenderTicket(ticketId)) },
                    onPanelClick = { navController.popBackStack(Rutas.Panel, inclusive = false) },
                    onCategoriasClick = { navController.navigate(Rutas.Categorias) },
                    onAjustesClick = { navController.navigate(Rutas.Ajustes) }
                )
            } else {
                PantallaDestino("Preparando bandeja", "Soporte TI")
            }
        }
        composable(Rutas.NuevoTicket) {
            val usuario by produceState<Usuario?>(initialValue = null) {
                val usuarioId = preferencias.usuarioId.first()
                value = repository.buscarUsuario(usuarioId)
            }
            val recibirTickets by preferencias.recibirTickets.collectAsState(initial = true)
            if (usuario?.rol == Rol.CLIENTE.name) {
                val usuarioActual = requireNotNull(usuario)
                val nuevoTicketViewModel: NuevoTicketViewModel = viewModel(
                    key = "nuevo-ticket-${usuarioActual.id}",
                    factory = NuevoTicketViewModelFactory(usuarioActual, repository)
                )
                NuevoTicketScreen(
                    vm = nuevoTicketViewModel,
                    mesaAbierta = recibirTickets,
                    onTicketCreado = { navController.popBackStack(Rutas.MisTickets, inclusive = false) },
                    onTicketsClick = { navController.popBackStack(Rutas.MisTickets, inclusive = false) },
                    onPerfilClick = { navController.navigate(Rutas.Perfil) }
                )
            } else if (usuario == null) {
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
                            if (usuarioActual.rol == Rol.ADMIN.name) Rutas.BandejaSoporte else Rutas.MisTickets,
                            inclusive = false
                        )
                    },
                    onNuevoClick = {
                        if (usuarioActual.rol == Rol.CLIENTE.name) navController.navigate(Rutas.NuevoTicket)
                    },
                    onPerfilClick = {
                        if (usuarioActual.rol == Rol.CLIENTE.name) navController.navigate(Rutas.Perfil)
                    }
                )
            } else {
                PantallaDestino("Cargando detalle", "Ticket")
            }
        }
        composable(route = Rutas.AtenderTicket, arguments = listOf(navArgument("ticketId") { type = NavType.IntType })) { entrada ->
            val ticketId = entrada.arguments?.getInt("ticketId") ?: return@composable
            val usuario by produceState<Usuario?>(initialValue = null) {
                value = repository.buscarUsuario(preferencias.usuarioId.first())
            }
            if (usuario?.rol == Rol.ADMIN.name) {
                val actual = requireNotNull(usuario)
                val vm: AtenderViewModel = viewModel(key = "atender-$ticketId", factory = AtenderViewModelFactory(ticketId, actual, repository))
                AtenderTicketScreen(vm) { navController.popBackStack() }
            } else PantallaDestino("Preparando atención", "Soporte TI")
        }
        composable(Rutas.Categorias) {
            val vm: CategoriasViewModel = viewModel(factory = factory)
            CategoriasScreen(
                vm = vm,
                onEditar = { navController.navigate(Rutas.formCategoria(it)) },
                onNueva = { navController.navigate(Rutas.formCategoria()) },
                onPanelClick = { navController.popBackStack(Rutas.Panel, inclusive = false) },
                onTicketsClick = { navController.navigate(Rutas.BandejaSoporte) },
                onAjustesClick = { navController.navigate(Rutas.Ajustes) }
            )
        }
        composable(Rutas.Perfil) {
            val usuario by produceState<Usuario?>(initialValue = null) { value = repository.buscarUsuario(preferencias.usuarioId.first()) }
            if (usuario?.rol == Rol.CLIENTE.name) {
                val actual = requireNotNull(usuario)
                val ticketsVm: MisTicketsViewModel = viewModel(key = "perfil-tickets-${actual.id}", factory = MisTicketsViewModelFactory(actual, repository))
                PerfilScreen(
                    usuario = actual, tickets = ticketsVm.todosLosTickets, preferencias = preferencias,
                    onTickets = { navController.popBackStack(Rutas.MisTickets, false) },
                    onIrTickets = { navController.popBackStack(Rutas.MisTickets, false) },
                    onNuevo = { navController.navigate(Rutas.NuevoTicket) },
                    onCambiarPerfil = { scope.launch { cambiarPerfil(Rol.ADMIN, repository, preferencias, navController) } },
                    onCerrarSesion = { scope.launch { cerrarSesion(sesionViewModel, preferencias, navController) } }
                )
            }
        }
        composable(Rutas.Ajustes) {
            val usuario by produceState<Usuario?>(initialValue = null) { value = repository.buscarUsuario(preferencias.usuarioId.first()) }
            if (usuario?.rol == Rol.ADMIN.name) {
                AjustesScreen(
                    usuario = requireNotNull(usuario), preferencias = preferencias,
                    onPanel = { navController.popBackStack(Rutas.Panel, false) },
                    onTickets = { navController.navigate(Rutas.BandejaSoporte) },
                    onCategorias = { navController.navigate(Rutas.Categorias) },
                    onCambiarPerfil = { scope.launch { cambiarPerfil(Rol.CLIENTE, repository, preferencias, navController) } },
                    onCerrarSesion = { scope.launch { cerrarSesion(sesionViewModel, preferencias, navController) } }
                )
            }
        }
        composable(route = Rutas.FormCategoria, arguments = listOf(navArgument("categoriaId") { type = NavType.IntType; defaultValue = 0 })) { entrada ->
            val categoriaId = entrada.arguments?.getInt("categoriaId") ?: 0
            val vm: CategoriasViewModel = viewModel(factory = factory)
            val categoria by produceState<com.tecsup.ayudacampus.data.local.entity.Categoria?>(initialValue = null, categoriaId) {
                value = if (categoriaId == 0) null else repository.buscarCategoria(categoriaId)
            }
            FormularioCategoriaScreen(categoria = categoria, onGuardar = { vm.guardar(it); navController.popBackStack() }, onCancelar = { navController.popBackStack() })
        }
    }
}

private suspend fun cambiarPerfil(
    rol: Rol,
    repository: AyudaRepository,
    preferencias: PreferenciasStore,
    navController: androidx.navigation.NavHostController
) {
    val usuario = repository.buscarUsuarioPorRol(rol.name) ?: return
    preferencias.cambiarPerfil(rol.name, usuario.id)
    val destino = if (rol == Rol.CLIENTE) Rutas.MisTickets else Rutas.Panel
    navController.navigate(destino) { popUpTo(0) { inclusive = true }; launchSingleTop = true }
}

private suspend fun cerrarSesion(
    sesionViewModel: SesionViewModel,
    preferencias: PreferenciasStore,
    navController: androidx.navigation.NavHostController
) {
    sesionViewModel.cerrarSesion()
    preferencias.cerrarSesion()
    navController.navigate(Rutas.Login) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
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
