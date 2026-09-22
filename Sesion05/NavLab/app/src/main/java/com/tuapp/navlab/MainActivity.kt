package com.tuapp.navlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuapp.navlab.navigation.Screen
import com.tuapp.navlab.screens.DetailScreen
import com.tuapp.navlab.screens.HomeScreen
import com.tuapp.navlab.screens.ListScreen
import com.tuapp.navlab.screens.ProfileScreen
import com.tuapp.navlab.ui.theme.NavLabTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NavLabTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.List.route) {
            ListScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.Detail.route) { backStackEntry ->

            val itemId =
                backStackEntry.arguments?.getString("itemId")?.toIntOrNull() ?: 0

            DetailScreen(
                navController = navController,
                itemId = itemId
            )
        }
    }
}