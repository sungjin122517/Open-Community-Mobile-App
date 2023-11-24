package com.example.finalproject.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.finalproject.screens.loginFlow.LoginScreen


//fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
//    navigation(
//        route = Graph.AUTHENTICATION,
//        startDestination = AuthScreen.Login.route
//    ) {
//        composable(route = AuthScreen.Login.route) {
//            LoginScreen(navController)
//        }
//    }
//}
//
//sealed class AuthScreen(val route: String) {
//    object Login: AuthScreen(route = "LOGIN")
//}