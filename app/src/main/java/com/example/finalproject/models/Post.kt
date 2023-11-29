package com.example.finalproject.models

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.finalproject.data.firestore.fetchData
import com.example.finalproject.data.getUserID
import com.example.finalproject.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    val id: String,
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

fun fetchPost(postID: String, context: Context): Post {
    // allow async fetching

//    getUserID(context) {
//        Log.d(TAG, "DocumentSnapshot data: $it")
//    }
    fetchData()
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


