package com.example.finalproject.data.service.impl

//import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostsService
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
//    private val auth: AuthService = AuthServiceImpl()
): PostsService {
    override val posts: Flow<List<Post>>
        get() = firestore.collection(POST_COLLECTION).dataObjects<Post>()

    override suspend fun getPost(postId: String): Post? {
        TODO("Not yet implemented")
    }

    override suspend fun save(post: Post): String {
        TODO("Not yet implemented")
    }

    override suspend fun update(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(post: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val POST_COLLECTION = "posts"
//        private const val SAVE_TASK_TRACE = "saveTask"
//        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}