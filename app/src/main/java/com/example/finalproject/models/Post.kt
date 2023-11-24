package com.example.finalproject.models

import java.util.Date

enum class PostCategory(val value: String, val description: String) {
    JUST_TALK("Just talk", "Posts that are casual and informal"),
    ACADEMIC("Academic", "Posts that are related to academic topics")
}

data class PostStatus(
    val viewCount: Int,
    val commentCount: Int,
    var saveCount: Int,
    var isSaved: Boolean,
)

class Post(
    val id: Int,
    val category: PostCategory,
    val title: String,
    val time: Date,
    val writer: String,         // should be another class User
    val text: String,
    private val viewCount: Int,
    private val commentCount: Int,
    private val saveCount: Int,
    private val isSaved: Boolean,
) {
    val status = PostStatus(viewCount, commentCount, saveCount,isSaved)
}

fun fetchPost(postID: Int): Post {
    return Post(
        postID,
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
}
