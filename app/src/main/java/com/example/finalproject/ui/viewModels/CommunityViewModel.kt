package com.example.finalproject.ui.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.savedPostIDs
import com.example.finalproject.data.service.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val service: CommunityService
): ViewModel() {
    var posts = service.posts
    var user = service.user
//    var post: Flow<Post?> get() = viewModelScope.launch {
//        service.getPost()
//    }


    var savedPOstIds = hashSetOf<String>()

    fun fetchPost(postId: String): Flow<Post?> {
        return service.getPost(postId)
//        val post = mutableStateOf(Post())
//        if (postId != null) {
//            viewModelScope.launch {
//                post.value =  service.getPost(postId) ?: post.value
//            }
//        }
//        return post
    }


    fun updateSaveCount(postId: String, newSaveCount: Int) {
        val updateMap = hashMapOf<String, Int>(
            "saveCount" to newSaveCount,
        )
        viewModelScope.launch() {
            service.updatePostField(postId, updateMap)
        }
        Log.d(TAG, "Paco: newSaveCount: $newSaveCount")
    }

    suspend fun updateSavePostIds() {
        Log.d(TAG, "Paco: new: ${savedPOstIds.toList()}")
        val updateMap = hashMapOf<String, List<String>>(
            "savedPostIds" to savedPOstIds.toList(),
        )
        service.updateUserField(updateMap)
    }

    fun onPostClicked(postId: String, openPostDetailScreen: (String) -> Unit) {
        state["post_id"] = postId
        openPostDetailScreen(postId)
    }

    fun onSaveClicked(context: Context, post: Post, isSaved: Boolean) {
        Log.d(TAG, "Paco: onSave before ${post.id}, is saved=$isSaved")
        viewModelScope.launch {
            if (isSaved) {
                savedPOstIds.remove(post.id)
                updateSavePostIds()
                updateSaveCount(post.id, post.saveCount-1)
            } else {
                savedPOstIds.add(post.id)
                updateSavePostIds()
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
            service.user.collect { user ->
                Log.d(TAG, "Paco: collect user")
                val savedPostsId = user?.savedPostIds
                if (savedPostsId != null) {
                    for (postId in savedPostsId) {
                        Log.d(TAG, "Paco: save $postId")
                        savedPOstIds.add(postId)
//                        storeSavedPostId(context, postId, true)
                    }
                }
                Log.d(TAG, "Paco: fetch and store: ${savedPOstIds.toList()}")
            }
//            sharedPref.edit()
//                .putStringSet("savedPostIds", savedPOstIds)
//                .apply()
        }
    }
}

//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ServiceModule {
//    @Binds
//    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//
//    …
//}
