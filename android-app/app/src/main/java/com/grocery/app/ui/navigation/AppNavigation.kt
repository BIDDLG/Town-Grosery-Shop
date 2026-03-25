package com.grocery.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.grocery.app.ui.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("cart") {
            CartScreen(navController = navController)
        }
        composable("checkout") {
            CheckoutScreen(navController = navController)
        }
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(navController = navController, productId = productId)
        }
        composable("admin") {
            AdminScreen(navController = navController)
        }
    }
}
