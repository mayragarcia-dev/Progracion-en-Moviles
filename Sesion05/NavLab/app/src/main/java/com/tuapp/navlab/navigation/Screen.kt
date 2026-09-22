package com.tuapp.navlab.navigation

sealed class Screen(val route: String) {

    // Pantalla de inicio - punto de entrada de la app
    object Home : Screen(route = "home")

    // Pantalla que muestra la lista de elementos
    object List : Screen(route = "list")

    // Pantalla del perfil del usuario
    object Profile : Screen(route = "profile")

    // Ruta con argumento
    // {itemId} es el placeholder que Navigation reemplaza
    // con el valor real al momento de navegar
    object Detail : Screen(route = "detail/{itemId}") {

        // Construye la ruta final sustituyendo el placeholder
        // por el valor real.
        fun createRoute(itemId: Int): String = "detail/$itemId"
    }
}
