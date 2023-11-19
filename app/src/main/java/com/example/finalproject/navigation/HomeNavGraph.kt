package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.components.BottomBarScreen
import com.example.finalproject.screens.CommunityScreen
import com.example.finalproject.screens.EventDetailsScreen
import com.example.finalproject.screens.EventScreen
import com.example.finalproject.screens.ProfileScreen
import com.example.finalproject.screens.ReportScreen
import com.example.finalproject.screens.ReportScreen
import com.example.finalproject.viewModels.AuthViewModel
import com.example.finalproject.viewModels.EventViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {

    val eventViewModel: EventViewModel = viewModel()

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Event.route
    ) {
        composable(
            route = BottomBarScreen.Event.route
        ) {
            EventScreen(navController, eventViewModel)
        }
        composable(
            route = BottomBarScreen.Post.route
        ) {
            CommunityScreen(navController = navController)
        }
        composable(
            route = BottomBarScreen.Profile.route
        ) {
            ProfileScreen()
        }
        composable(
            route = Graph.EVENT_DETAILS
        ) {
//            val event = navController.currentBackStackEntry?.savedStateHandle?.get<Event>("event")
            EventDetailsScreen(navController, eventViewModel)
        }
//        composable(
//            route = Graph.POST_DETAILS
//        ) {
//            PostDetailsScreen()
//        }
        composable(
            route = Graph.PROFILE
        ) {
            ProfileScreen()
        }
        composable(
            route = Graph.REPORT
        ) {
            ReportScreen(navController)
        }
    }
}

