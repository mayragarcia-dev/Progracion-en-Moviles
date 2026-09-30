package com.tecsup.garcia

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class DrawerScreen(val route: String, val title: String, val icon: ImageVector) {
    object Inicio : DrawerScreen("inicio", "Inicio", Icons.Default.Home)
    object MisPedidos : DrawerScreen("mis_pedidos", "Mis pedidos", Icons.Default.List)
    object Favoritos : DrawerScreen("favoritos", "Favoritos", Icons.Default.Favorite)
    object Perfil : DrawerScreen("perfil", "Perfil", Icons.Default.Person)
    object CerrarSesion : DrawerScreen("cerrar_sesion", "Cerrar sesión", Icons.Default.ExitToApp)
}

@Composable
fun AppDrawer(
    currentRoute: String,
    onDestinationSelected: (DrawerScreen) -> Unit,
    onCloseDrawer: () -> Unit
) {
    val items = listOf(
        DrawerScreen.Inicio,
        DrawerScreen.MisPedidos,
        DrawerScreen.Favoritos,
        DrawerScreen.Perfil,
        DrawerScreen.CerrarSesion
    )

    // Hito 4: Estructura del NavigationDrawer con ModalDrawerSheet
    ModalDrawerSheet(
        drawerContainerColor = Color.White
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        items.forEach { screen ->
            NavigationDrawerItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    onDestinationSelected(screen)
                    onCloseDrawer()
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
