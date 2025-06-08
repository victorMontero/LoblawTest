package com.example.loblawtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loblawtest.presentation.shoppinglist.ShoppingListScreen
import com.example.loblawtest.presentation.offers.OfferListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "offers") {
        composable("offers") {
            OfferListScreen(navController = navController)
        }
        composable("shopping_list") {
            ShoppingListScreen(navController = navController)
        }
    }
}