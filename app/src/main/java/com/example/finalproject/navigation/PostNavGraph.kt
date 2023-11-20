package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.screens.CommunityScreen
import com.example.finalproject.screens.PostDetailsScreen
import com.example.finalproject.ui.theme.FinalProjectTheme
@Composable
fun PostNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Graph.COMMUNITY) {
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
        PostNavGraph(navController = rememberNavController())
    }
}