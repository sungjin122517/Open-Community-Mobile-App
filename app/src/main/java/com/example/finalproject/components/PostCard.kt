package com.example.finalproject.components

//import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.ComposeCompilerApi
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
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.models.Post
import com.example.finalproject.models.PostCategory
import com.example.finalproject.models.PostStatus
import com.example.finalproject.models.fetchPost
import com.example.finalproject.navigation.Graph
import com.example.finalproject.ui.theme.darkerBackground

import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(modifier: Modifier, post: Post, navController: NavController) {
    Column {

        // Define the layout and style of the card
        Card(
            modifier = modifier
                .fillMaxWidth(),
    //        elevation = CardDefaults.cardElevation(
    //            defaultElevation = 8.dp
    //        ),
            onClick = {
                navController.navigate("post_detail/0")
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
                PostCardHeader(post.category, post.time, navController)
                // Display the user name and the post time
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom=8.dp)
                )
                // Display the post content
                Text(
                    text = post.text,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )

                PostCardStatus(post.status)

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
fun PostCardHeader(category: PostCategory, date: Date, navController: NavController) {
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
            text = category.value,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = date.toString(),
            color = Color.Gray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        PostDropDownMenu(navController)
    }
}

@Composable
fun PostCardStatus(status: PostStatus) {
    val (saveCount, setSaveCount) = remember {
        mutableStateOf(status.saveCount)
    }

    fun saveHandler() {
        if (status.isSaved) {
            // unsaved
            setSaveCount(saveCount-1)
            status.isSaved = false
        } else {
            setSaveCount(saveCount+1)
            status.isSaved = true
            // update local db
        }
        // update post
    }

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
                text = status.viewCount.toString(),
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
//            Text(
//                text = "Comments",
//                fontSize = 14.sp
//            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = status.commentCount.toString(),
                fontSize = 16.sp
            )
        }
        // Display the number of shares
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {saveHandler()})
        ) {
//                    Icon(
//                        imageVector = Icons.Default.Share,
//                        contentDescription = "Shares",
//                        tint = Color.Green
//                    )
            Icon(
                imageVector = if (status.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Comments"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = saveCount.toString(),
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun PostDropDownMenu(navController: NavController) {
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
            DropdownMenuItem(text = { Text("Report") }, onClick = { navController.navigate(Graph.REPORT) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    FinalProjectTheme(darkTheme = true) {
        val post = Post(
            0,
            PostCategory.ACADEMIC,
            "This is title",
            Date(10),
            "User 00",
            "content",
            0,
            5,
            0,
            false,
        )

        PostCard(modifier = Modifier, post, NavController(LocalContext.current))
    }
//    }
}
