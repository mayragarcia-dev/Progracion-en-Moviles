package com.tecsup.ayudacampus

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.DatosIniciales
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import com.tecsup.ayudacampus.ui.navigation.NavAyuda
import com.tecsup.ayudacampus.ui.theme.AyudaCampusTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun AyudaCampusUi() {
    val context = LocalContext.current.applicationContext
    val database = remember(context) { AyudaDatabase.obtener(context) }
    val repository = remember(database) {
        AyudaRepositoryRoom(
            usuarioDao = database.usuarioDao(),
            categoriaDao = database.categoriaDao(),
            ticketDao = database.ticketDao(),
            comentarioDao = database.comentarioDao(),
            notificacionDao = database.notificacionDao()
        )
    }
    val preferencias = remember(context) { PreferenciasStore(context) }
    val modoOscuro by preferencias.modoOscuro.collectAsState(initial = false)

    AyudaCampusTheme(darkTheme = modoOscuro) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavAyuda(repository = repository, preferencias = preferencias)
        }
    }
}

class AyudaApp : Application() {
    val database: AyudaDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        AyudaDatabase.obtener(this)
    }
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            DatosIniciales.sembrar(database)
        }
    }
}
