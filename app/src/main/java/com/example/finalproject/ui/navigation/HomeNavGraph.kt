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
import com.example.finalproject.ui.screens.PostCreateScreen
import com.example.finalproject.ui.screens.PostDetailsScreen
import com.example.finalproject.ui.screens.ProfileScreen
import com.example.finalproject.ui.screens.ReportScreen
import com.example.finalproject.ui.viewModels.EventViewModel
import com.example.finalproject.ui.viewModels.PostViewModel
import com.example.finalproject.ui.viewModels.ProfileViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    val eventViewModel: EventViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val postViewModel: PostViewModel = hiltViewModel()

    postViewModel.fetchAndStoreSavedPostIds(context)
    eventViewModel.fetchAndStoreSavedEventIds(context)
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
            ProfileScreen(navController, profileViewModel, postViewModel) { postId ->
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
            ProfileScreen(navController, profileViewModel, postViewModel) { postId ->
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
                ReportScreen(postID, navController, postViewModel::onReportSubmit)
            } else {
                navController.navigateUp()
            }
        }
//        postNavGraph(navController)
        composable(BottomBarScreen.Post.route) {
            CommunityScreen(navController, postViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
        composable(Graph.POST_DETAILS) { navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("postID")
            if (postID != null) {
                PostDetailsScreen(postID = postID, navController = navController, postViewModel)
            } else {
                navController.navigateUp()
            }
        }
        composable(Graph.POST_CREATE) { navBackStackEntry ->
            PostCreateScreen(navController = navController, onPostCreate = postViewModel::onPostCreate)
        }
    }
}



