package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.mibodega.ui.cliente.navegacion.AppNavegacion

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    AppNavegacion(
        navController = navController
    )
}