package com.example.finalproject.ui.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.Preferences
import com.example.finalproject.data.model.Comment
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.savedPostIDs
import com.example.finalproject.data.service.PostService
import com.example.finalproject.data.service.UserService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postService: PostService,
    private val userService: UserService
): ViewModel() {
    var posts = postService.posts
    var user = userService.user

    private var savedPostIds = mutableSetOf<String>()

    fun fetchPost(postId: String): Flow<Post?> {
        return postService.getPost(postId)
    }

    fun fetchComments(postId: String): Flow<List<Comment>> {
        return postService.getPostComment(postId)
    }

//    fun fetchPost(userId: String):  Flow<List<Post>> {
//        return service.getPosts(postId)
//    }


    fun updateSaveCount(postId: String, newSaveCount: Int) {
        val updateMap = hashMapOf<String, Int>(
            "saveCount" to newSaveCount,
        )
        viewModelScope.launch() {
            postService.updatePostField(postId, updateMap)
        }
        Log.d(TAG, "Paco: newSaveCount: $newSaveCount")
    }

    suspend fun updateSavePostIds(newFieldValue: FieldValue) {
        val updateMap = hashMapOf(
            "savedPostIds" to newFieldValue,
        )
        userService.updateUserField(updateMap)
    }

    fun onPostCreate(context: Context, category: String, title: String, content: String) {
        Preferences.getUserId(context) {userId ->
            val post = Post(
                writerId = userId!!,
                category = category,
                title = title,
                content = content,
                time = Timestamp.now()
            )

            Log.d(TAG, "Paco: create Post = $post")
            viewModelScope.launch {
                postService.createPost(post).addOnSuccessListener { postRef ->
                    userService.addMyPostId(postRef.id)

                }
            }
        }
    }

    fun onCommentSubmit(context: Context, post: Post, commentText: String) {
        Log.d(TAG, "Paco: submit comment for post = ${post.id}, commet = $commentText")
        Preferences.getUserId(context) {userId ->
            val report = mapOf(
                "writerId" to userId!!,
                "content" to commentText,
                "time" to Timestamp.now(),
                "sameWriter" to (userId == post.writerId),
                "deleted" to false
            )
            viewModelScope.launch {
                postService.addComment(post, report)
            }
        }
    }

    fun onReportSubmit(context: Context, postId: String, reportReason: String) {
        Log.d(TAG, "Paco: submit report:$postId")
        Preferences.getUserId(context) {userId ->
            val report = mapOf(
                "reporterId" to userId!!,
                "reportReason" to reportReason,
                "submitDateTime" to Timestamp.now()
            )
            viewModelScope.launch {
                postService.addReport(postId, report)
            }
        }
    }

    fun onSaveClicked(context: Context, post: Post, isSaved: Boolean) {
        Log.d(TAG, "Paco: onSave before ${post.id}, is saved=$isSaved")
        viewModelScope.launch {
            if (isSaved) {
                savedPostIds.remove(post.id)
                updateSavePostIds(FieldValue.arrayRemove(post.id))
                updateSaveCount(post.id, post.saveCount-1)
            } else {
                savedPostIds.add(post.id)
                updateSavePostIds(FieldValue.arrayUnion(post.id))
                updateSaveCount(post.id, post.saveCount+1)
            }
        }
        Log.d(TAG, "Paco: onSave after ${post.id}, is saved=$isSaved")
    }

    fun onRefresh() {}



    @SuppressLint("CommitPrefEdits")
    fun fetchAndStoreSavedPostIds(context: Context) {
        val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        Log.d(TAG, "Paco: fetchAndStoreSavedPostIds")
        viewModelScope.launch() {
            context.savedPostIDs.edit { it.clear() }
            userService.user.collect { user ->
                Log.d(TAG, "Paco: collect user")
                val savedPostsId = user?.savedPostIds
                if (savedPostsId != null) {
                    for (postId in savedPostsId) {
                        Log.d(TAG, "Paco: save $postId")
                        savedPostIds.add(postId)
//                        storeSavedPostId(context, postId, true)
                    }
                }
                Log.d(TAG, "Paco: fetch and store Post = ${savedPostIds.toList()}")
            }
//            sharedPref.edit()
//                .putStringSet("savedPostIds", savedPOstIds)
//                .apply()
        }
    }

//    fun fetchEvents(date: Timestamp = Timestamp.now()) {
//        Log.d(TAG, "Paco: today is $date")
//        viewModelScope.launch {
//            val events = service.getEvents(date)
//            Log.d(TAG, "Paco: events = $events")
//
//        }
//    }

}

//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ServiceModule {
//    @Binds
//    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//
//    …
//}
