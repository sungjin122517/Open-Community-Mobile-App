package com.example.finalproject.data.service

//import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.Comment
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.model.User
import kotlinx.coroutines.flow.Flow


interface CommunityService {
    val posts: Flow<List<Post>>
    val user: Flow<User?>

//    val getSavedPostId: Flow<List<String>>
    fun getPost(postId: String): Flow<Post?>
    suspend fun getPostStatus(postId: String): Flow<PostStatus?>
    fun getPostComment(postId: String): Flow<List<Comment>>
    suspend fun updatePostField(postId: String, updateMap: Map<String, Any>)
    suspend fun addComment(post: Post, commentMap: Map<String, Any>)
    suspend fun addReport(postId: String, reportMap: Map<String, Any>)
     suspend fun updateUserField(updateMap: Map<String, Any>)
    suspend fun delete(post: String)

    suspend fun incrementView(post: Post)
}