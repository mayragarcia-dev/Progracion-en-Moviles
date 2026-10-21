package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.domain.model.Prioridad
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NuevoTicketScreen(
    vm: NuevoTicketViewModel,
    onTicketCreado: () -> Unit,
    onTicketsClick: () -> Unit,
    onPerfilClick: () -> Unit = {},
    mesaAbierta: Boolean = true
) {
    val state by vm.uiState.collectAsState()
    val categorias by vm.categorias.collectAsState()

    val esOscuro = isSystemInDarkTheme() || MaterialTheme.colorScheme.background.red < 0.2f
    val fondoPantalla = if (esOscuro) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val textoOscuro = if (esOscuro) Color(0xFFF8FAFC) else Color(0xFF1E293B)
    val bordeGris = if (esOscuro) Color(0xFF334155) else Color(0xFFE2E8F0)
    val cianEstudiante = if (esOscuro) Color(0xFF00ACC1) else Color(0xFF00838F)
    val grisTextoSecundario = if (esOscuro) Color(0xFF94A3B8) else Color(0xFF64748B)
    val cianFondoBanner = if (esOscuro) Color(0xFF132E38) else Color(0xFFE0F7FA)
    val navBarFondo = if (esOscuro) Color(0xFF1E293B) else Color.White
    val cardFondo = if (esOscuro) Color(0xFF1E293B) else Color.White

    LaunchedEffect(state.ticketCreadoId) {
        if (state.ticketCreadoId != null) onTicketCreado()
    }

    Scaffold(
        containerColor = fondoPantalla,
        bottomBar = {
            NavigationBar(
                containerColor = navBarFondo,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onTicketsClick,
                    icon = {
                        Icon(
                            imageVector = IconList,
                            contentDescription = "Tickets",
                            tint = grisTextoSecundario
                        )
                    },
                    label = { Text("Tickets", color = grisTextoSecundario, fontSize = 12.sp) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = IconAdd,
                            contentDescription = "Nuevo",
                            tint = cianEstudiante
                        )
                    },
                    label = {
                        Text(
                            "Nuevo",
                            color = cianEstudiante,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = cianFondoBanner
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onPerfilClick,
                    icon = {
                        Icon(
                            imageVector = IconPerson,
                            contentDescription = "Perfil",
                            tint = grisTextoSecundario
                        )
                    },
                    label = { Text("Perfil", color = grisTextoSecundario, fontSize = 12.sp) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (!mesaAbierta) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        "Mesa de ayuda cerrada",
                        Modifier.padding(20.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header superior
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onTicketsClick,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = IconArrowBack,
                                contentDescription = "Volver",
                                tint = textoOscuro
                            )
                        }
                        Text(
                            text = "Nuevo ticket",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = textoOscuro
                            )
                        )
                        Spacer(Modifier.size(36.dp))
                    }

                    // Seccion Categoria
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Categoría",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = textoOscuro
                            )
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            categorias.forEach { categoria ->
                                CategoryChip(
                                    categoria = categoria,
                                    isSelected = state.categoriaSeleccionada?.id == categoria.id,
                                    esOscuro = esOscuro,
                                    cianEstudiante = cianEstudiante,
                                    onSelect = { vm.seleccionarCategoria(categoria) }
                                )
                            }
                        }
                        state.errorCategoria?.let { ErrorCampo(it) }
                    }

                    // Campo Titulo
                    OutlinedTextField(
                        value = state.titulo,
                        onValueChange = vm::cambiarTitulo,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Título",
                                color = if (esOscuro) Color(0xFF64748B) else Color(0xFF94A3B8),
                                fontSize = 15.sp
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = customTextFieldColors(cardFondo, cianEstudiante, bordeGris, textoOscuro),
                        isError = state.errorTitulo != null,
                        supportingText = { state.errorTitulo?.let { mensaje -> Text(mensaje) } }
                    )

                    // Campo Ubicacion
                    OutlinedTextField(
                        value = state.ubicacion,
                        onValueChange = vm::cambiarUbicacion,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Ubicación",
                                color = if (esOscuro) Color(0xFF64748B) else Color(0xFF94A3B8),
                                fontSize = 15.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = IconLocation,
                                contentDescription = null,
                                tint = cianEstudiante,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = customTextFieldColors(cardFondo, cianEstudiante, bordeGris, textoOscuro)
                    )

                    // Seccion Prioridad
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Prioridad",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = textoOscuro
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Prioridad.entries.forEach { prioridad ->
                                PriorityButton(
                                    prioridad = prioridad,
                                    isSelected = state.prioridad == prioridad,
                                    esOscuro = esOscuro,
                                    onSelect = { vm.seleccionarPrioridad(prioridad) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Campo Descripcion
                    OutlinedTextField(
                        value = state.descripcion,
                        onValueChange = vm::cambiarDescripcion,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Descripción",
                                color = if (esOscuro) Color(0xFF64748B) else Color(0xFF94A3B8),
                                fontSize = 15.sp
                            )
                        },
                        minLines = 4,
                        maxLines = 5,
                        shape = RoundedCornerShape(14.dp),
                        colors = customTextFieldColors(cardFondo, cianEstudiante, bordeGris, textoOscuro),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isError = state.errorDescripcion != null,
                        supportingText = { state.errorDescripcion?.let { mensaje -> Text(mensaje) } }
                    )

                    // Banner de Tiempo Estimado
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = cianFondoBanner
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = IconClock,
                                contentDescription = null,
                                tint = cianEstudiante,
                                modifier = Modifier.size(20.dp)
                            )
                            val tiempoEstimado = when (state.prioridad) {
                                Prioridad.ALTA -> "4 horas"
                                Prioridad.MEDIA -> "24 horas"
                                Prioridad.BAJA -> "72 horas"
                            }
                            Text(
                                text = "Tiempo de respuesta estimado: $tiempoEstimado",
                                color = cianEstudiante,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    state.errorEnvio?.let { ErrorCampo(it) }

                    // Boton Enviar Ticket
                    Button(
                        onClick = vm::enviar,
                        enabled = !state.enviando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cianEstudiante,
                            disabledContainerColor = cianEstudiante.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            text = if (state.enviando) "Enviando…" else "Enviar ticket",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    categoria: Categoria,
    isSelected: Boolean,
    esOscuro: Boolean,
    cianEstudiante: Color,
    onSelect: () -> Unit
) {
    val backgroundColor = if (isSelected) cianEstudiante else (if (esOscuro) Color(0xFF1E293B) else Color.White)
    val contentColor = if (isSelected) Color.White else (if (esOscuro) Color(0xFFCBD5E1) else Color(0xFF475569))
    val borderColor = if (isSelected) Color.Transparent else (if (esOscuro) Color(0xFF334155) else Color(0xFFCBD5E1))

    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(50),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.height(42.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = obtenerIconoCategoria(categoria),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = categoria.nombre,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun PriorityButton(
    prioridad: Prioridad,
    isSelected: Boolean,
    esOscuro: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedBgColor = when (prioridad) {
        Prioridad.ALTA -> Color(0xFFDC2626)
        Prioridad.MEDIA -> Color(0xFFD97706)
        Prioridad.BAJA -> Color(0xFF059669)
    }
    val backgroundColor = if (isSelected) selectedBgColor else (if (esOscuro) Color(0xFF1E293B) else Color.White)
    val contentColor = if (isSelected) Color.White else (if (esOscuro) Color(0xFFCBD5E1) else Color(0xFF475569))
    val borderColor = if (isSelected) Color.Transparent else (if (esOscuro) Color(0xFF334155) else Color(0xFFCBD5E1))

    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier.height(46.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = when (prioridad) {
                    Prioridad.BAJA -> "Baja"
                    Prioridad.MEDIA -> "Media"
                    Prioridad.ALTA -> "Alta"
                },
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun customTextFieldColors(
    containerColor: Color,
    focusedBorder: Color,
    unfocusedBorder: Color,
    textColor: Color
) = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = containerColor,
    unfocusedContainerColor = containerColor,
    disabledContainerColor = containerColor,
    focusedBorderColor = focusedBorder,
    unfocusedBorderColor = unfocusedBorder,
    focusedTextColor = textColor,
    unfocusedTextColor = textColor
)

@Composable
private fun ErrorCampo(mensaje: String) {
    Text(
        mensaje,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 4.dp)
    )
}

private fun obtenerIconoCategoria(categoria: Categoria): ImageVector =
    when (categoria.icono.lowercase()) {
        "pc", "hardware" -> IconHardware
        "code", "software" -> IconSoftware
        "wifi", "red / wi-fi" -> IconWifi
        "key", "cuenta" -> IconCuenta
        "printer", "impresión", "impresion" -> IconPrinter
        else -> IconHardware
    }

private val IconHardware: ImageVector by lazy {
    ImageVector.Builder(
        name = "Hardware",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(3f, 4f)
            lineTo(21f, 4f)
            lineTo(21f, 15f)
            lineTo(3f, 15f)
            close()
            moveTo(12f, 15f)
            lineTo(12f, 19f)
            moveTo(8f, 19f)
            lineTo(16f, 19f)
        }
    }.build()
}

private val IconSoftware: ImageVector by lazy {
    ImageVector.Builder(
        name = "Software",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(8f, 7f)
            lineTo(3f, 12f)
            lineTo(8f, 17f)
            moveTo(16f, 7f)
            lineTo(21f, 12f)
            lineTo(16f, 17f)
        }
    }.build()
}

private val IconWifi: ImageVector by lazy {
    ImageVector.Builder(
        name = "Wifi",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4f, 8f)
            curveTo(8f, 4f, 16f, 4f, 20f, 8f)
            moveTo(7f, 12f)
            curveTo(10f, 9f, 14f, 9f, 17f, 12f)
            moveTo(10f, 16f)
            curveTo(11f, 14.5f, 13f, 14.5f, 14f, 16f)
            moveTo(12f, 19f)
            lineTo(12.01f, 19f)
        }
    }.build()
}

private val IconCuenta: ImageVector by lazy {
    ImageVector.Builder(
        name = "Cuenta",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(10f, 14f)
            curveTo(12.2f, 14f, 14f, 12.2f, 14f, 10f)
            curveTo(14f, 7.8f, 12.2f, 6f, 10f, 6f)
            curveTo(6f, 12.2f, 7.8f, 14f, 10f, 14f)
            close()
            moveTo(13f, 13f)
            lineTo(20f, 20f)
            moveTo(18f, 18f)
            lineTo(20f, 16f)
            moveTo(16f, 16f)
            lineTo(18f, 14f)
        }
    }.build()
}

private val IconPrinter: ImageVector by lazy {
    ImageVector.Builder(
        name = "Printer",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(7f, 8f)
            lineTo(7f, 3f)
            lineTo(17f, 3f)
            lineTo(17f, 8f)
            moveTo(5f, 8f)
            lineTo(19f, 8f)
            curveTo(20.1f, 8f, 21f, 8.9f, 21f, 10f)
            lineTo(21f, 15f)
            lineTo(17f, 15f)
            lineTo(17f, 21f)
            lineTo(7f, 21f)
            lineTo(7f, 15f)
            lineTo(3f, 15f)
            lineTo(3f, 10f)
            curveTo(3f, 8.9f, 3.9f, 8f, 5f, 8f)
            close()
        }
    }.build()
}

private val IconLocation: ImageVector by lazy {
    ImageVector.Builder(
        name = "Location",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 21f)
            curveTo(12f, 21f, 19f, 15f, 19f, 10f)
            curveTo(19f, 6.1f, 15.9f, 3f, 12f, 3f)
            curveTo(8.1f, 3f, 5f, 6.1f, 5f, 10f)
            curveTo(5f, 15f, 12f, 21f, 12f, 21f)
            close()
            moveTo(12f, 12f)
            curveTo(13.1f, 12f, 14f, 11.1f, 14f, 10f)
            curveTo(14f, 8.9f, 13.1f, 8f, 12f, 8f)
            curveTo(10.9f, 8f, 10f, 8.9f, 10f, 10f)
            curveTo(10f, 11.1f, 10.9f, 12f, 12f, 12f)
            close()
        }
    }.build()
}

private val IconClock: ImageVector by lazy {
    ImageVector.Builder(
        name = "Clock",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 21f)
            curveTo(16.97f, 21f, 21f, 16.97f, 21f, 12f)
            curveTo(21f, 7.03f, 16.97f, 3f, 12f, 3f)
            curveTo(7.03f, 3f, 3f, 7.03f, 3f, 12f)
            curveTo(3f, 16.97f, 7.03f, 21f, 12f, 21f)
            close()
            moveTo(12f, 7f)
            lineTo(12f, 12f)
            lineTo(15.5f, 14f)
        }
    }.build()
}

private val IconArrowBack: ImageVector by lazy {
    ImageVector.Builder(
        name = "ArrowBack",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19f, 12f)
            lineTo(5f, 12f)
            moveTo(12f, 19f)
            lineTo(5f, 12f)
            lineTo(12f, 5f)
        }
    }.build()
}

private val IconList: ImageVector by lazy {
    ImageVector.Builder(
        name = "List",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(8f, 6f)
            lineTo(20f, 6f)
            moveTo(8f, 12f)
            lineTo(20f, 12f)
            moveTo(8f, 18f)
            lineTo(20f, 18f)
            moveTo(4f, 6f)
            lineTo(4.01f, 6f)
            moveTo(4f, 12f)
            lineTo(4.01f, 12f)
            moveTo(4f, 18f)
            lineTo(4.01f, 18f)
        }
    }.build()
}

private val IconAdd: ImageVector by lazy {
    ImageVector.Builder(
        name = "Add",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 5f)
            lineTo(12f, 19f)
            moveTo(5f, 12f)
            lineTo(19f, 12f)
        }
    }.build()
}

private val IconPerson: ImageVector by lazy {
    ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 12f)
            curveTo(14.2f, 12f, 16f, 10.2f, 16f, 8f)
            curveTo(16f, 5.8f, 14.2f, 4f, 12f, 4f)
            curveTo(9.8f, 4f, 8f, 5.8f, 8f, 8f)
            curveTo(8f, 10.2f, 9.8f, 12f, 12f, 12f)
            close()
            moveTo(6f, 20f)
            curveTo(6f, 16.7f, 8.7f, 14f, 12f, 14f)
            curveTo(15.3f, 14f, 18f, 16.7f, 18f, 20f)
        }
    }.build()
}
