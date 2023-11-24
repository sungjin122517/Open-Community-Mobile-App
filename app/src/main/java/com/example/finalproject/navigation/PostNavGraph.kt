package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.finalproject.components.BottomBarScreen
import com.example.finalproject.screens.CommunityScreen
import com.example.finalproject.screens.PostDetailsScreen
import com.example.finalproject.ui.theme.FinalProjectTheme
//@Composable
fun NavGraphBuilder.postNavGraph(navController: NavHostController) {
    navigation(startDestination = Graph.COMMUNITY, route=BottomBarScreen.Post.route) {
        composable(Graph.COMMUNITY) {
            CommunityScreen(navController)
        }
        composable(Graph.POST_DETAILS) {navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getInt("postID")
            if (postID != null) {
                PostDetailsScreen(postID = postID, navController = navController)
            } else {
                PostDetailsScreen(postID = 0, navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun MyNavGraphPreview() {
    FinalProjectTheme(darkTheme = true) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            route = "post_preview",
            startDestination = BottomBarScreen.Post.route
        ) {
            postNavGraph(navController)
        }
    }
}