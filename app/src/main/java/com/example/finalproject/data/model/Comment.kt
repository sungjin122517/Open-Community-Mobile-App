package com.example.finalproject.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class Comment(
    val content: String = "",
    val time: Timestamp = Timestamp.now(),
    val isWriter: Boolean = false,
    val isDeleted: Boolean = false
)


fun fetchComments(postID: Int, commentCount: Int): Array<Comment> {
    val comments = mutableListOf<Comment>()
    for (i in 1..commentCount) {
        comments.add(Comment())
    }
    return comments.toTypedArray()
}

