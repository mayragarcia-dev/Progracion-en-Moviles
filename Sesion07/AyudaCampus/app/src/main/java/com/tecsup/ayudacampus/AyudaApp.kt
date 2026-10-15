package com.tecsup.ayudacampus

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.ayudacampus.data.local.AyudaCampusDatabase
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.ui.theme.AyudaCampusTheme
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import kotlinx.coroutines.delay

@Composable
fun AyudaApp() {
    val context = LocalContext.current.applicationContext
    val database = remember(context) { AyudaCampusDatabase.obtenerInstancia(context) }
    val repository = remember(database) {
        AyudaRepositoryRoom(
            categoriaDao = database.categoriaDao(),
            ticketDao = database.ticketDao(),
            comentarioDao = database.comentarioDao()
        )
    }
    val factory = remember(repository) { AyudaViewModelFactory(repository) }
    val categoriasViewModel: CategoriasViewModel = viewModel(factory = factory)
    var mostrarSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1_200)
        mostrarSplash = false
    }

    AyudaCampusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (mostrarSplash) {
                SplashScreen()
            } else {
                CategoriasScreen(vm = categoriasViewModel, onEditar = {})
            }
        }
    }
}
