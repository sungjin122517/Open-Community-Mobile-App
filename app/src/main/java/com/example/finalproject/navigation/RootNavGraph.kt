package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.screens.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.EVENT) {
            HomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val EVENT = "event_graph"
    const val EVENT_DETAILS = "event_detail_graph/{event}"
    const val POST_DETAILS = "post_detail/{postID}"
    const val PROFILE = "profile_graph"
    const val COMMUNITY = "community"
    const val REPORT = "report_graph"
}