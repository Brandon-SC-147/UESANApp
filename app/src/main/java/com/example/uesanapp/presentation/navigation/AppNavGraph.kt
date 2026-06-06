package com.example.uesanapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uesanapp.presentation.Permissions.GaleryPermissionsScreen
import com.example.uesanapp.presentation.auth.LoginScreen
import com.example.uesanapp.presentation.auth.RegisterScreen
import com.example.uesanapp.presentation.favorites.FavoritesScreen
import com.example.uesanapp.presentation.home.HomeScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") {
            DrawerScaffold(navController) {
                HomeScreen(navController)
            }
        }
        composable("permissions") {
            DrawerScaffold(navController) {
                GaleryPermissionsScreen()
            }
        }
        composable("favorites") {
            DrawerScaffold(navController) {
                FavoritesScreen(navController)
            }
        }
    }

}
