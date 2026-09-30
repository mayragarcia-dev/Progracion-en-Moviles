package com.tecsup.garcia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentRoute by remember { mutableStateOf(DrawerScreen.Inicio.route) }

    // Hito 5: Navegación real desde los ítems del drawer con ModalNavigationDrawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                onDestinationSelected = { screen ->
                    currentRoute = screen.route
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        val title = when (currentRoute) {
            DrawerScreen.Inicio.route -> "TECSUP Store - Inicio"
            DrawerScreen.MisPedidos.route -> "Mis Pedidos"
            DrawerScreen.Favoritos.route -> "Mis Favoritos"
            DrawerScreen.Perfil.route -> "Perfil de Usuario"
            DrawerScreen.CerrarSesion.route -> "Cerrar Sesión"
            else -> "TECSUP Store"
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(title, color = Color.White, fontWeight = FontWeight.Bold) 
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF4A148C)
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                when (currentRoute) {
                    DrawerScreen.Inicio.route -> PantallaCarritoContent()
                    DrawerScreen.MisPedidos.route -> SimplePlaceholderScreen("Aquí se mostrarán tus pedidos realizados.")
                    DrawerScreen.Favoritos.route -> SimplePlaceholderScreen("Aquí se mostrarán tus productos favoritos.")
                    DrawerScreen.Perfil.route -> SimplePlaceholderScreen("Configuración y datos del perfil de usuario.")
                    DrawerScreen.CerrarSesion.route -> SimplePlaceholderScreen("Has cerrado sesión correctamente.")
                }
            }
        }
    }
}

@Composable
fun SimplePlaceholderScreen(mensaje: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PantallaCarritoContent() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    val productos = remember { mutableStateListOf<Producto>() }

    val subtotal = productos.sumOf { it.precio * it.cantidad }
    val igv = subtotal * 0.18
    val total = subtotal + igv

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            snackbarMessage = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (productos.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3E5F5))
                        .padding(16.dp)
                ) {
                    Text("Productos: ${productos.size}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    TotalRow("Subtotal", "S/ ${"%.2f".format(subtotal)}")
                    TotalRow("IGV (18%)", "S/ ${"%.2f".format(igv)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("TOTAL", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "S/ ${"%.2f".format(total)}",
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF4A148C)
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray,
                    focusedLabelColor = Color(0xFF4A148C),
                    unfocusedLabelColor = Color.DarkGray,
                    focusedBorderColor = Color(0xFF4A148C),
                    unfocusedBorderColor = Color.LightGray
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = textFieldColors
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio (S/)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        colors = textFieldColors
                    )
                    OutlinedTextField(
                        value = cantidad,
                        onValueChange = { cantidad = it },
                        label = { Text("Cantidad") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = textFieldColors
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val p = precio.toDoubleOrNull() ?: 0.0
                        val c = cantidad.toIntOrNull() ?: 0
                        if (nombre.isNotBlank() && p > 0 && c > 0) {
                            productos.add(Producto(nombre, p, c))
                            nombre = ""; precio = ""; cantidad = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A148C)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("AGREGAR", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFF5F5F5))

            Box(modifier = Modifier.weight(1f)) {
                if (productos.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Tu carrito está vacío", color = Color.Gray, fontWeight = FontWeight.Medium)
                        Text("Agrega tu primer producto", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(productos) { prod ->
                            TarjetaProducto(
                                producto = prod,
                                onEliminar = { productos.remove(prod) },
                                onFavoritoClick = { snackbarMessage = "Agregado a Favoritos: ${prod.nombre}" },
                                onCompartirClick = { snackbarMessage = "Compartiendo: ${prod.nombre}" },
                                onReportarClick = { snackbarMessage = "Reportado: ${prod.nombre}" }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TotalRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
    }
}
