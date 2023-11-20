package com.example.finalproject.components

//import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(modifier: Modifier, post: Post, navController: NavController) {
    // Define the layout and style of the card
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = {
            navController.navigate("post_detail/0")
        },
        shape = RectangleShape,
    ) {
        // Define the content of the card
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Display Header
            PostCardHeader(post.category, post.time)
            // Display the user name and the post time
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            // Display the post content
            Text(
                text = post.text,
                fontSize = 16.sp,
                modifier = Modifier
//                    .padding(8.dp)
                    .fillMaxWidth()
            )

            PostCardStatus(post.status)

        }
    }
}


@Composable
fun PostCardHeader(category: PostCategory, date: Date) {
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
            text = category.toString().lowercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = date.toString(),
            color = Color.Gray,
            fontSize = 14.sp
        )
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
//                    Icon(
//                        imageVector = Icon.Default.Favorite,
//                        contentDescription = "Likes",
//                        tint = Color.Red
//                    )
            Text(
                text = "View",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status.viewCount.toString(),
                fontSize = 14.sp
            )
        }
        // Display the number of comments
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
//                    Icon(
//                        imageVector = Icons.Default.Comment,
//                        contentDescription = "Comments",
//                        tint = Color.Blue
//                    )
            Text(
                text = "Comments",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status.commentCount.toString(),
                fontSize = 14.sp
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
            Text(
                text = "Save",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = saveCount.toString(),
                fontSize = 14.sp
            )

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
