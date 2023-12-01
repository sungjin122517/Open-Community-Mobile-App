package com.example.finalproject.data.service

import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val user: Flow<User?>

//    suspend fun getSavedPostId(): List<String>
    suspend fun getUser(userId: String): Flow<User?>
    suspend fun save(user: User): String
    suspend fun update(updateMap: Map<String, Any>)
    suspend fun delete(user: User)
}