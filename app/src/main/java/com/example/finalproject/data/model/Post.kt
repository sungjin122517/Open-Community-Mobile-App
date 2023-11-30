package com.example.finalproject.data.model

import android.content.Context
import com.example.finalproject.data.firestore.fetchData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
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

//class Post(
//    val id: String,
//    val category: PostCategory,
//    val title: String,
//    val time: Date,
//    val writer: String,         // should be another class User
//    val text: String,
//    private val viewCount: Int,
//    private val commentCount: Int,
//    private val saveCount: Int,
//    private val isSaved: Boolean,
//) {
//    val status = PostStatus(viewCount, commentCount, saveCount,isSaved)
//}

class Post(
    @DocumentId val id: String = "",
    val category: String = "Academic",
    val title: String = "",
    val time: Timestamp = Timestamp(Date(0)),
    val writerId: String = "",         // should be another class User
    val content: String = "",
    val deleted: Boolean = false,
    private val viewCount: Int = 0,
    private val commentCount: Int = 0,
    private val saveCount: Int = 0,
    private val isSaved: Boolean = false,
) {
    val status = PostStatus(viewCount, commentCount, saveCount, isSaved)
}

fun fetchPost(postID: String, context: Context): Post {
    // allow async fetching

//    getUserID(context) {
//        Log.d(TAG, "DocumentSnapshot data: $it")
//    }
    fetchData()
    return Post(
        postID,
        PostCategory.ACADEMIC.name,
        "This is title",
        Timestamp(Date(0)),
        "User 00",
        "content",
        false,
        5,
        0,
        0,
    )

}


