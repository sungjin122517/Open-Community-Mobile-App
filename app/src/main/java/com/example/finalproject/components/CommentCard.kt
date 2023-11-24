package com.example.finalproject.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.models.Comment
import com.example.finalproject.navigation.Graph
import com.example.finalproject.ui.theme.FinalProjectTheme
import java.util.Date


@Composable
fun CommentCard(comment: Comment) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape,
//            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceVariant),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ){
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    if (comment.isWriter) {
                        Text(text = "Writer", color = Color.Cyan, fontSize = 16.sp)
                    } else {
                        Text(text = "Anonymous", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        comment.createTime.toString(),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                }

                Text(comment.content)
            }
        }

        Spacer(     // horizontal divisor
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }

    // TODO: Long press delete
}

@Preview(showBackground = true)
@Composable
fun CommentCardPreview() {
    FinalProjectTheme(darkTheme = true) {
        CommentCard(Comment(content = "HI", createTime = Date(10)))
    }
}