package com.example.orders

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.orders.core.data.UserPreferences
import com.example.orders.core.ui.UserPreferencesViewModel
import com.example.orders.todo.ui.order.OrderScreen
import com.example.orders.todo.ui.orders.OrdersScreen

const val ordersRoute = "orders"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val onCloseOrder = {
        Log.d("MyAppNavHost", "navigate back to list")
        navController.popBackStack()
    }
    val userPreferencesViewModel =
        viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    val userPreferencesUiState by userPreferencesViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = UserPreferences()
    )
    val myAppViewModel = viewModel<AppViewModel>(factory = AppViewModel.Factory)
    NavHost(
        navController = navController,
        startDestination = ordersRoute
    ) {
        composable(ordersRoute) {
            OrdersScreen()
        }
        composable(
            route = "$ordersRoute/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        {
            OrderScreen(
                orderId = it.arguments?.getInt("id"),
                onClose = { onCloseOrder() }
            )
        }
//        composable(route = "$ordersRoute-new")
//        {
//            OrderScreen(
//                orderId = null,
//                onClose = { onCloseOrder() }
//            )
//        }

    }
    LaunchedEffect(userPreferencesUiState.token) {
//        if (userPreferencesUiState.token.isNotEmpty()) {
//            delay(1500)
//            Log.d("MyAppNavHost", "Lauched effect navigate to orders")
//            Api.tokenInterceptor.token = userPreferencesUiState.token
//            myAppViewModel.setToken(userPreferencesUiState.token)
//            navController.navigate(ordersRoute) {
//                popUpTo(0)
//            }
//        }

    }
    LaunchedEffect(true){
        Log.d("MyAppNavHost", "Lauched effect navigate to orders")
        myAppViewModel.setToken(userPreferencesUiState.token)
        navController.navigate(ordersRoute) {
            popUpTo(0)
        }
    }
}

