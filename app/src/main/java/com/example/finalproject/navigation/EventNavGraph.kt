package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.screens.PostDetailsScreen
import com.example.finalproject.screens.ReportScreen

@Composable
fun EventDetailsNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.EVENT_DETAILS,
        startDestination = Graph.EVENT_DETAILS,
    ) {
        composable(
            route = Graph.REPORT
        ) {
//            ReportScreen(navController)
        }
    }
}