package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.components.PostCard
import com.example.finalproject.dataStore
import com.example.finalproject.data.model.fetchPost
import com.example.finalproject.data.service.module.AppModule
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.viewModels.PostsViewModel
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
    val view_model: PostsViewModel = hiltViewModel()
    val posts = view_model.posts.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold (
        topBar = {TopAppBar(title = {Text("Community")})}
    ) { it ->
        Column(modifier = Modifier.padding(it)) {

            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )

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
                        //                    .padding(it)
                        .fillMaxSize()
                        .pullRefresh(state),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(posts.value) {post ->
                        PostCard(
                            Modifier,
                            post = post,
                            navController
                        )
                    }

                }
            }else {
                LazyColumn(
                    modifier = Modifier
                        //                    .padding(it)
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
}

fun checkPostFeed(): Boolean {
    return true
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityTopBar() {
    Column {


    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomBar(navController) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ){
                    CommunityScreen(navController)
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
//
//                    Text(text = currentDestination?.route?:"hi")
                }
            }
        )
    }
}