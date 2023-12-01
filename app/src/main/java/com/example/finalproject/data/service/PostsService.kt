package com.example.finalproject.data.service

//import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.model.User
import com.example.finalproject.data.utils.await
import com.google.firebase.firestore.DocumentId
import kotlinx.coroutines.flow.Flow
import java.util.Date



interface PostsService {
    val posts: Flow<List<Post>>
    val user: Flow<User?>

//    val getSavedPostId: Flow<List<String>>
    suspend fun getPost(postId: String): Flow<Post?>
    suspend fun getPostStatus(postId: String): Flow<PostStatus?>
    suspend fun save(post: Post): String
    suspend fun update(post: Post)
    suspend fun updatePostField(postId: String, updateMap: Map<String, Any>)
     suspend fun updateUserField(updateMap: Map<String, Any>)
    suspend fun delete(post: String)
}