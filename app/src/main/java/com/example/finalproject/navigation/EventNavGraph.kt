package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun EventDetailsNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.EVENT_DETAILS,
        startDestination = Graph.EVENT_DETAILS,
    ) {

    }
}