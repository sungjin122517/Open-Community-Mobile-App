package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.ui.screens.HomeScreen
import com.example.finalproject.ui.screens.loginFlow.LoginScreen
import com.example.finalproject.ui.screens.loginFlow.SignUpScreen
import com.example.finalproject.ui.screens.loginFlow.VerifyEmailScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
//    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        composable(route = Graph.AUTHENTICATION) {
            LoginScreen(
                navController = navController,
                navigateToForgotPasswordScreen = {
//                    navController.navigate(ForgotPasswordScreen.route)
                },
            )
        }
        composable(route = Graph.HOME) {
            HomeScreen()
        }
        composable(route = Graph.SIGN_UP) {
            SignUpScreen(navController = navController)
        }
        composable(route = Graph.VERIFY_EMAIL) {
            VerifyEmailScreen(
                navController = navController,
//                navigateToHomeScreen = {
//
//                }
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "event_graph"
    const val EVENT_DETAILS = "event_detail_graph/{event}"
    const val POST_DETAILS = "post_detail/{postID}"
    const val PROFILE = "profile_graph"
    const val POST = "post"
    const val COMMUNITY = "community"
    const val REPORT = "report_graph/{docId}"
    const val VERIFY_EMAIL = "verify_email"
    const val SIGN_UP = "sign_up"
}