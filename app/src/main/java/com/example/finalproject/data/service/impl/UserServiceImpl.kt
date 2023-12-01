package com.example.finalproject.data.service.impl

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.data.model.User
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostsService
import com.example.finalproject.data.service.UserService
import com.example.finalproject.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class UserServiceImpl @Inject constructor(
//    private val firestore: FirebaseFirestore,
//    private val auth: AuthService,
//): UserService  {
//    override val user: Flow<User?>
//        get() = firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).dataObjects<User>()
////    override suspend fun getSavedPostId(): List<String> {
//////        return user.onEach {
//////            it.savedPostIds
//////        }.
////    }
//
//    override suspend fun getUser(userId: String): Flow<User?> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun save(user: User): String {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun update(updateMap: Map<String, Any>) {
//        firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).update(updateMap).await()
//    }
//
//    override suspend fun delete(user: User) {
//        TODO("Not yet implemented")
//    }
//    companion object {
//        private const val USER_ID_FIELD = "userId"
//        private const val USER_COLLECTION = "users"
//    }
//}