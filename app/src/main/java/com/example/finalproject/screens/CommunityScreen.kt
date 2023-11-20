package com.example.finalproject.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.finalproject.components.PostCard
import com.example.finalproject.models.fetchPost
import com.example.finalproject.ui.theme.FinalProjectTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityScreen(navController: NavController) {
    /*
    * Features:
    *   1. View PostFeed
    *      - No post
    *      - Render PostFeed
    *
    *   2. Refresh screen
    *      - display reloading
    *      - refresh animation
    *      - update PostFeed
    *
    *   3. Navigate Regular/Popular
    *
    *   4. Create new post with button
    *
    */
//    val communityNavController = rememberNavController()

    Scaffold (
        topBar = {TopAppBar(title = {Text("Community")})}
    ) {
        // state for listen to refreshing
        var isRefreshing by remember {
            mutableStateOf(false)
        }

        val (checkPostFeed, setCheckPostFeed) = remember {
            mutableStateOf(checkPostFeed())
        }
        val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
            isRefreshing = true
            setCheckPostFeed(!checkPostFeed)
            isRefreshing = false
        })

        if (checkPostFeed) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .pullRefresh(state),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(5) {
                    PostCard(
                        Modifier,
                        post = fetchPost(0),
                        navController
                    )
                }

            }
        }else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .pullRefresh(state),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Text("No Post Yet", modifier = Modifier)
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing, state = state,
            modifier = Modifier
                .fillMaxSize()
        )

   }
}

fun checkPostFeed(): Boolean {
    return false
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        CommunityScreen(navController = NavController(LocalContext.current))
    }
}