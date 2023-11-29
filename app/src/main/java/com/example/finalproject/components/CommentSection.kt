package com.example.finalproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.model.Comment
import com.example.finalproject.ui.theme.FinalProjectTheme
//import eu.bambooapps.material3.pullrefresh.pullRefresh
import java.util.Date

/*
* The Comment Section
* - Display all the comments
* - show text when there is no comments
*
*/
@Composable
fun CommentSection(comments: Array<Comment>, modifier: Modifier) {

    if (comments.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (comment in comments) {
                if (comment.isDeleted) continue

                item() {
                    CommentCard(comment)
                }
            }
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No Comment Yet")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview() {
    FinalProjectTheme(darkTheme = true) {
        val comments = mutableListOf<Comment>()
        for (i in 1..5) {
            comments.add(Comment("Hi", Date(10)))
        }
        Surface {
            CommentSection(comments = comments.toTypedArray(), modifier = Modifier)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmptyCommentSectionPreview() {
    FinalProjectTheme(darkTheme = true) {
        val comments = mutableListOf<Comment>()

        Surface {
            CommentSection(comments = comments.toTypedArray(), modifier = Modifier)
        }
    }
}
