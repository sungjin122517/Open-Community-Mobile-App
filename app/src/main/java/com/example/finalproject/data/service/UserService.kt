package com.example.finalproject.data.service

import com.example.finalproject.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val user: Flow<User?>

    suspend fun updateUserField(updateMap: Map<String, Any>)

    fun addMyPostId(postId: String)
}