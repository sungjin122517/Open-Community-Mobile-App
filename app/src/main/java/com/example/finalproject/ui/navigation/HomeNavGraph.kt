package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.finalproject.ui.viewModels.CommunityViewModel
import com.example.finalproject.ui.viewModels.ProfileViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {

    val eventViewModel: EventViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val communityViewModel: CommunityViewModel = hiltViewModel()
    communityViewModel.fetchAndStoreSavedPostIds(LocalContext.current)
//    eventViewModel.fetchEvents()

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
            ProfileScreen(navController, profileViewModel, communityViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
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
            ProfileScreen(navController, profileViewModel, communityViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
//        composable(
//            route = Graph.REPORT
//        ) {
//            ReportScreen(navController)
//        }
        composable(route = Graph.REPORT) {navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("docId")
            if (postID != null) {
                ReportScreen(postID, navController, communityViewModel::onReportSubmit)
            } else {
                navController.navigateUp()
            }
        }
//        postNavGraph(navController)
        composable(BottomBarScreen.Post.route) {
            CommunityScreen(navController, communityViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
        composable(Graph.POST_DETAILS) { navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("postID")
            if (postID != null) {
                PostDetailsScreen(postID = postID, navController = navController, communityViewModel)
            } else {
                navController.navigateUp()
            }
        }
    }
}



