package com.example.finalproject.ui.components

//import android.content.Context
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
//import com.example.finalproject.data.checkSavedPost
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.model.fetchPost
import com.example.finalproject.data.savedPostIDs
import com.example.finalproject.ui.navigation.Graph
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat

import java.util.Date

val DEFAULT_DATE = Date(0)

@SuppressLint("SimpleDateFormat")
val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")

@SuppressLint("SimpleDateFormat")
val minutesFormatter = SimpleDateFormat("m")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    modifier: Modifier,
    post: Post,
    navController: NavController,
    isSaved: Boolean,
    onSaveClicked: (Context, Post, Boolean) -> Unit,
    openPostDetailScreen: (String) -> Unit
) {
    Column {

        // Define the layout and style of the card
        Card(
            modifier = modifier
                .fillMaxWidth(),
    //        elevation = CardDefaults.cardElevation(
    //            defaultElevation = 8.dp
    //        ),
            onClick = {
//                navController.navigate("post_detail/0")
                openPostDetailScreen(post.id)
            },
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
//            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            // Define the content of the card
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Display Header
                PostCardHeader(post.id, post.category, post.time.toDate(), navController)
                // Display the user name and the post time
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom=8.dp)
                )
                // Display the post content
                Text(
                    text = post.content,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                PostCardStatus(post, isSaved, onSaveClicked)

            }
        }

        Spacer(     // horizontal divisor
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}


@Composable
fun PostCardHeader(postId: String, category: String, date: Date, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

//                    Icon(
//                        imageVector = Icon.Default.Favorite,
//                        contentDescription = "Likes",
//                        tint = Color.Red
//                    )
//        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = if (date != DEFAULT_DATE) dateFormatter.format(date) else "just now",
            color = Color.Gray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        PostDropDownMenu(postId, navController)
    }
}

@Composable
fun PostCardStatus(
    post: Post, isSaved: Boolean,
    onSaveClicked: (Context, Post, Boolean) -> Unit
) {
    val context = LocalContext.current
//    val (saveCount, setSaveCount) = remember {
//        mutableStateOf(post.saveCount)
//    }

    // Display the post stats
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,

        ) {
        // Display the number of likes
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.RemoveRedEye,
                contentDescription = "Views"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.viewCount.toString(),
                fontSize = 16.sp
            )
        }
        // Display the number of comments
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Comment,
                contentDescription = "Comments"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.commentCount.toString(),
                fontSize = 16.sp
            )
        }
        // Display the number of shares
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {
                Log.d(TAG, "Paco: Click Saved")
                onSaveClicked(context, post, isSaved)
//                if (isSaved) setSaveCount(saveCount-1) else setSaveCount(saveCount+1)
            })
        ) {
            Icon(
                imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Save Post"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.saveCount.toString(),
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun PostDropDownMenu(postId: String, navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

        var expended by remember {
            mutableStateOf(false)
        }
        IconButton(
            onClick = {
                /* TODO: Add code to navigate to 'report' screen */
//                    navController.navigate(Graph.REPORT)
                expended = true

            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Report"
            )
        }
        DropdownMenu(
            expanded = expended,
            onDismissRequest = {
                expended = false
            },
            modifier = Modifier
        ) {
            DropdownMenuItem(
                text = { Text("Report") },
                onClick = { navController.navigate("report_graph/$postId") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    FinalProjectTheme(darkTheme = true) {
        val post = fetchPost("TEST_POST_ID", LocalContext.current)

        PostCard(modifier = Modifier, post, NavController(LocalContext.current), false,{ c, post, d -> null }, {s ->}
        )

    }
//    }
}
fun saveHandler(post: Post) {
        if (post.isSaved) {
            // unsaved
//            setSaveCount(saveCount-1)
            post.saveCount -= 1
            post.isSaved = false
        } else {
//            setSaveCount(saveCount+1)
            post.saveCount += 1
            post.isSaved = true
            // update local db
        }
        // update post
    }
