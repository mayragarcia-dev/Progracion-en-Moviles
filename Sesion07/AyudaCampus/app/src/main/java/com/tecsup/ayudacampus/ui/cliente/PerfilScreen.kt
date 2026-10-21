package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.model.TicketResumen
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.domain.model.EstadoTicket
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    usuario: Usuario,
    tickets: StateFlow<List<TicketResumen>>,
    preferencias: PreferenciasStore,
    onTickets: () -> Unit,
    onNuevo: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit,
    onIrTickets: () -> Unit = onTickets
) {
    val lista by tickets.collectAsState()
    val modoOscuro by preferencias.modoOscuro.collectAsState(initial = false)
    val notificaciones by preferencias.notificaciones.collectAsState(initial = true)
    val scope = rememberCoroutineScope()

    val abiertos = lista.count {
        it.estado == EstadoTicket.ABIERTO.name || it.estado == EstadoTicket.EN_PROCESO.name
    }
    val resueltos = lista.count {
        it.estado == EstadoTicket.RESUELTO.name || it.estado == EstadoTicket.CERRADO.name
    }

    // Colores dinamicos segun Modo Oscuro
    val fondoPantalla = if (modoOscuro) Color(0xFF0F172A) else Color(0xFFF8FAFC)
    val tarjetaFondo = if (modoOscuro) Color(0xFF1E293B) else Color.White
    val textoOscuro = if (modoOscuro) Color(0xFFF8FAFC) else Color(0xFF1E293B)
    val bordeGris = if (modoOscuro) Color(0xFF334155) else Color(0xFFE2E8F0)
    val grisTextoSecundario = if (modoOscuro) Color(0xFF94A3B8) else Color(0xFF64748B)
    val cianEstudiante = if (modoOscuro) Color(0xFF00ACC1) else Color(0xFF00838F)
    val cianFondoAvatar = if (modoOscuro) Color(0xFF132E38) else Color(0xFFE0F7FA)
    val cianTextoAvatar = if (modoOscuro) Color(0xFF80DEEA) else Color(0xFF00838F)
    val navBarFondo = if (modoOscuro) Color(0xFF1E293B) else Color.White

    Scaffold(
        containerColor = fondoPantalla,
        bottomBar = {
            NavigationBar(
                containerColor = navBarFondo,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onIrTickets,
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
                    selected = false,
                    onClick = onNuevo,
                    icon = {
                        Icon(
                            imageVector = IconAdd,
                            contentDescription = "Nuevo",
                            tint = grisTextoSecundario
                        )
                    },
                    label = { Text("Nuevo", color = grisTextoSecundario, fontSize = 12.sp) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = IconPerson,
                            contentDescription = "Perfil",
                            tint = cianEstudiante
                        )
                    },
                    label = {
                        Text(
                            "Perfil",
                            color = cianEstudiante,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = cianFondoAvatar
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onIrTickets,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = IconArrowBack,
                        contentDescription = "Volver",
                        tint = textoOscuro
                    )
                }
                Text(
                    text = "Perfil",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )
                )
                Spacer(Modifier.size(36.dp))
            }

            // Card de Usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = tarjetaFondo),
                border = BorderStroke(1.dp, bordeGris)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(cianFondoAvatar),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = IconPerson,
                            contentDescription = null,
                            tint = cianTextoAvatar,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = usuario.nombre,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = textoOscuro,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = usuario.correo,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = grisTextoSecundario,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = cianFondoAvatar
                        ) {
                            Text(
                                text = "Estudiante",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                color = cianTextoAvatar,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // Fila de Tarjetas de Contadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PerfilContadorCard(
                    valor = "$abiertos",
                    titulo = "Abiertos",
                    colorTexto = if (modoOscuro) Color(0xFFFBBF24) else Color(0xFFD97706),
                    colorFondo = if (modoOscuro) Color(0xFF3A2E12) else Color(0xFFFFF8E1),
                    modifier = Modifier.weight(1f)
                )
                PerfilContadorCard(
                    valor = "$resueltos",
                    titulo = "Resueltos",
                    colorTexto = if (modoOscuro) Color(0xFF4ADE80) else Color(0xFF16A34A),
                    colorFondo = if (modoOscuro) Color(0xFF143821) else Color(0xFFDCFCE7),
                    modifier = Modifier.weight(1f)
                )
                PerfilContadorCard(
                    valor = "${lista.size}",
                    titulo = "Total",
                    colorTexto = cianTextoAvatar,
                    colorFondo = cianFondoAvatar,
                    modifier = Modifier.weight(1f)
                )
            }

            // Banner Boton Cambiar de Perfil
            Surface(
                onClick = onCambiarPerfil,
                shape = RoundedCornerShape(16.dp),
                color = cianEstudiante,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = IconSwap,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Cambiar de perfil",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = "Modo práctica: ir a vista Soporte TI",
                            color = Color(0xFFE0F7FA),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp
                            )
                        )
                    }
                    Icon(
                        imageVector = IconChevronRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Seccion Preferencias
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Preferencias",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = tarjetaFondo),
                    border = BorderStroke(1.dp, bordeGris)
                ) {
                    Column {
                        PreferenciaItem(
                            titulo = "Modo oscuro",
                            detalle = "Cambia el tema de la app",
                            activa = modoOscuro,
                            textoOscuro = textoOscuro,
                            grisTextoSecundario = grisTextoSecundario,
                            cianEstudiante = cianEstudiante,
                            onChange = { scope.launch { preferencias.guardarModoOscuro(it) } }
                        )
                        HorizontalDivider(color = bordeGris)
                        PreferenciaItem(
                            titulo = "Notificaciones",
                            detalle = "Avisar cambios de estado",
                            activa = notificaciones,
                            textoOscuro = textoOscuro,
                            grisTextoSecundario = grisTextoSecundario,
                            cianEstudiante = cianEstudiante,
                            onChange = { scope.launch { preferencias.guardarNotificaciones(it) } }
                        )
                    }
                }
            }

            // Banner DataStore
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = cianFondoAvatar
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Se guardan en el dispositivo",
                        color = grisTextoSecundario,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = "con DataStore",
                        color = cianTextoAvatar,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            // Boton Cerrar Sesion
            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFFEF4444)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFEF4444),
                    containerColor = tarjetaFondo
                )
            ) {
                Text(
                    text = "Cerrar sesión",
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

@Composable
private fun PerfilContadorCard(
    valor: String,
    titulo: String,
    colorTexto: Color,
    colorFondo: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorFondo
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = valor,
                color = colorTexto,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = titulo,
                color = colorTexto,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Composable
private fun PreferenciaItem(
    titulo: String,
    detalle: String,
    activa: Boolean,
    textoOscuro: Color,
    grisTextoSecundario: Color,
    cianEstudiante: Color,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textoOscuro,
                    fontSize = 15.sp
                )
            )
            Text(
                text = detalle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = grisTextoSecundario,
                    fontSize = 12.sp
                )
            )
        }
        Switch(
            checked = activa,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = cianEstudiante,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF64748B),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
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

private val IconSwap: ImageVector by lazy {
    ImageVector.Builder(
        name = "Swap",
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
            moveTo(20f, 8f)
            lineTo(4f, 8f)
            moveTo(16f, 4f)
            lineTo(20f, 8f)
            lineTo(16f, 12f)
            moveTo(4f, 16f)
            lineTo(20f, 16f)
            moveTo(8f, 12f)
            lineTo(4f, 16f)
            lineTo(8f, 20f)
        }
    }.build()
}

private val IconChevronRight: ImageVector by lazy {
    ImageVector.Builder(
        name = "ChevronRight",
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
            moveTo(9f, 18f)
            lineTo(15f, 12f)
            lineTo(9f, 6f)
        }
    }.build()
}
