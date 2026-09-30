package com.tecsup.garcia

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaProducto(
    producto: Producto,
    onEliminar: () -> Unit,
    onFavoritoClick: () -> Unit = {},
    onCompartirClick: () -> Unit = {},
    onReportarClick: () -> Unit = {}
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "S/ ${"%.2f".format(producto.precio)}  x  ${producto.cantidad}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "S/ ${"%.2f".format(producto.precio * producto.cantidad)}",
                    color = Color(0xFF4A148C), // Morado según solicitud
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = onEliminar) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFB71C1C)
                    )
                }

                // 3-dot menu icon + DropdownMenu
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opciones",
                            tint = Color.DarkGray
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = {
                                menuExpanded = false
                                onFavoritoClick()
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Favoritos",
                                    tint = Color(0xFFD81B60)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Compartir") },
                            onClick = {
                                menuExpanded = false
                                onCompartirClick()
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Share,
                                    contentDescription = "Compartir",
                                    tint = Color(0xFF4A148C)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Reportar") },
                            onClick = {
                                menuExpanded = false
                                onReportarClick()
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = "Reportar",
                                    tint = Color(0xFFE65100)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
