package com.example.finalproject.navigation

import android.telecom.Call.Details
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.finalproject.components.BottomBarScreen
import com.example.finalproject.screens.EventDetailsScreen
import com.example.finalproject.screens.EventScreen
import com.example.finalproject.screens.HomeScreen
import com.example.finalproject.screens.PostScreen
import com.example.finalproject.screens.ProfileScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.EVENT,
        startDestination = BottomBarScreen.Event.route
    ) {
        composable(route = BottomBarScreen.Event.route) {
            EventScreen()
        }
        composable(route = BottomBarScreen.Post.route) {
            PostScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        eventDetailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.eventDetailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.EVENT_DETAILS,
        startDestination = DetailsScreen.EventDetails.route
    ) {
        composable(route = DetailsScreen.EventDetails.route) {
            EventDetailsScreen()
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object EventDetails: DetailsScreen(route = "EVENT_DETAILS")
    object Report : DetailsScreen(route = "REPORT")
}

