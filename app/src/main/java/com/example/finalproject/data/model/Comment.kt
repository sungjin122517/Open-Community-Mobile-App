package com.example.finalproject.data.model

import java.util.Date

data class Comment(
    val content: String,
    val createTime: Date,
    val isWriter: Boolean = false,
    val isDeleted: Boolean = false
)


fun fetchComments(postID: Int, commentCount: Int): Array<Comment> {
    val comments = mutableListOf<Comment>()
    for (i in 1..commentCount) {
        comments.add(Comment("Hi", Date(10)))
    }
    return comments.toTypedArray()
}

