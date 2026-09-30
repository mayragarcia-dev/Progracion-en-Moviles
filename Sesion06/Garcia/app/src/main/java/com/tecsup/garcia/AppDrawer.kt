package com.tecsup.garcia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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

    ModalDrawerSheet(
        drawerContainerColor = Color.White
    ) {
        // Hito 6: Encabezado del drawer (avatar/iniciales y datos del usuario)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A148C))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MG",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A148C)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Mayra García",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "mayra.garcia@tecsup.edu.pe",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Hito 6: Destino activo resaltado visualmente
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationDrawerItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                selected = isSelected,
                onClick = {
                    onDestinationSelected(screen)
                    onCloseDrawer()
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFFF3E5F5), // Color de fondo distinto al resto
                    selectedTextColor = Color(0xFF4A148C),
                    selectedIconColor = Color(0xFF4A148C),
                    unselectedContainerColor = Color.Transparent,
                    unselectedTextColor = Color.DarkGray,
                    unselectedIconColor = Color.DarkGray
                )
            )
        }
    }
}
