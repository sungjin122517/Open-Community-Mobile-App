package com.example.finalproject.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.components.CommentSection
import com.example.finalproject.components.PostCard
import com.example.finalproject.data.model.Comment
import com.example.finalproject.data.model.fetchComments
import com.example.finalproject.data.model.fetchPost
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.white
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import java.util.Date



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostDetailsScreen(postID: String, navController: NavController) {
    // TODO: Obtain comments given post id
    val context = LocalContext.current
    // States
    val (post, setPost) = remember {
        mutableStateOf(fetchPost(postID, context))
    }

    val (comments, setComments) = remember {
        mutableStateOf(fetchComments(0, post.status.commentCount))
    }

    val (commentCount, setCommentCount) = remember {
        mutableIntStateOf(post.status.commentCount)
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        isRefreshing = true

        // check weather post have new comments
        val tmpPost = fetchPost(postID, context)
        if (0 != commentCount) {
            setPost(tmpPost)
            setComments(fetchComments(0, 0))
        }

        isRefreshing = false
    })

    Scaffold (
        topBar = { PostDetailTopBar(navController)},
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            PostCard(Modifier, post, navController)
            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            )
            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            CommentSection(comments, modifier = Modifier
                .pullRefresh(state))
//            Text("Something went wrong.")
//            Text("Please try again.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailTopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Post Detail", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = white
                )
            }
        },
//        actions = {
//            IconButton(
//                onClick = {
//                    /* TODO: Add code to navigate to 'report' screen */
//                    navController.navigate(Graph.REPORT)
//
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Warning,
//                    contentDescription = "Report",
//                )
//            }
//        }
    )
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        val comments = mutableListOf<Comment>()
        for (i in 1..5) {
            comments.add(Comment("Hi", Date(10)))
        }

        PostDetailsScreen("TEST_POST_ID", navController = NavController(LocalContext.current))
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PostDetailScreenNoCommentPreview() {
//    FinalProjectTheme(darkTheme = true) {
//        val comments = mutableListOf<Comment>()
//
//
//        PostDetailsScreen(0, navController = NavController(LocalContext.current))
//    }
//}