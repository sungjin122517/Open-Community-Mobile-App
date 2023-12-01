package com.example.finalproject.ui.viewModels

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.USER_ID
import com.example.finalproject.data.getSavedPost
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.removeSavedPostId
import com.example.finalproject.data.savedPostIDs
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostsService
import com.example.finalproject.data.service.impl.PostsServiceImpl
import com.example.finalproject.data.storeSavedPostId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val service: PostsService
): ViewModel() {
    var posts = service.posts

    var user = service.user

    var savedPOstIds = hashSetOf<String>()


    fun updateSaveCount(postId: String, newSaveCount: Int) {
        val updateMap = hashMapOf<String, Int>(
            "saveCount" to newSaveCount,
        )
        viewModelScope.launch() {
            service.updatePostField(postId, updateMap)
        }
    }

    suspend fun updateSavePostIds() {
        Log.d(TAG, "Paco: new: ${savedPOstIds.toList()}")
        val updateMap = hashMapOf<String, List<String>>(
            "savedPostIds" to savedPOstIds.toList(),
        )
        service.updateUserField(updateMap)
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



    fun fetchAndStoreSavedPostIds(context: Context) {
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
