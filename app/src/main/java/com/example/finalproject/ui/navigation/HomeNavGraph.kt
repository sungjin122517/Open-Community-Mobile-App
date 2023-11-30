package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.ui.components.BottomBarScreen
import com.example.finalproject.ui.screens.CommunityScreen
import com.example.finalproject.ui.screens.EventDetailsScreen
import com.example.finalproject.ui.screens.EventScreen
import com.example.finalproject.ui.screens.PostDetailsScreen
import com.example.finalproject.ui.screens.ProfileScreen
import com.example.finalproject.ui.screens.ReportScreen
import com.example.finalproject.ui.viewModels.EventViewModel

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
//        postNavGraph(navController)
        composable(BottomBarScreen.Post.route) {
            CommunityScreen(navController)
        }
        composable(Graph.POST_DETAILS) { navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("postID")
            if (postID != null) {
                PostDetailsScreen(postID = postID, navController = navController)
            } else {
                PostDetailsScreen(postID = "TEST_POST_ID", navController = navController)
            }
        }
    }
}



