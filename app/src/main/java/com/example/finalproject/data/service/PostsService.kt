package com.example.finalproject.data.service

//import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.google.firebase.firestore.DocumentId
import kotlinx.coroutines.flow.Flow
import java.util.Date



interface PostsService {
    val posts: Flow<List<Post>>
    suspend fun getPost(postId: String): Post?
    suspend fun save(post: Post): String
    suspend fun update(post: Post)
    suspend fun delete(post: String)
}