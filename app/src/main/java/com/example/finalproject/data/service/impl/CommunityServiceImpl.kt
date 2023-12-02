package com.example.finalproject.data.service.impl

//import com.example.finalproject.data.model.Post
import android.content.ContentValues
import android.util.Log
import androidx.tracing.Trace
import com.example.finalproject.data.model.Comment
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.model.User
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.CommunityService
import com.example.finalproject.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthService,
): CommunityService {
    override val posts: Flow<List<Post>>
        get() {
            Log.d(ContentValues.TAG, "Paco: fetch posts")
            return firestore.collection(POST_COLLECTION).dataObjects<Post>()
        }

    override val user: Flow<User?>
        get() = firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).dataObjects<User>()

    override fun getPost(postId: String): Flow<Post?> =
        firestore.collection(POST_COLLECTION).document(postId).dataObjects<Post>()

    override suspend fun getPostStatus(postId: String): Flow<PostStatus?> {
        Log.d(ContentValues.TAG, "Paco: get post status")
        return firestore.collection(POST_STATUS_COLLECTION).document(postId).dataObjects<PostStatus>()
    }

    override fun getPostComment(postId: String): Flow<List<Comment>> {
        Log.d(ContentValues.TAG, "Paco: get post status")
        return firestore.collection(POST_COLLECTION).document(postId)
            .collection(COMMENT_COLLECTION).dataObjects<Comment>()
    }

//    suspend fun getSavedPostId(postId: String): List<String>{
//        return userc
//    }

//    override val getSavedPostId: Flow<List<String>>
//        get() = firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).get("saved")

//    override suspend fun getPost(postId: String): Post? =
//        firestore.collection(POST_COLLECTION).document(postId).get().await().toObject()

    override suspend fun updatePostField(postId: String, updateMap: Map<String, Any>) {
        Trace.beginSection(UPDATE_POST_TRACE)
        firestore.collection(POST_COLLECTION).document(postId).update(updateMap).await()
        Trace.endSection()
    }

    override suspend fun updateUserField(updateMap: Map<String, Any>) {
        firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!).update(updateMap).await()
    }

    override suspend fun delete(post: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_COLLECTION = "users"
        private const val POST_COLLECTION = "posts"
        private const val COMMENT_COLLECTION = "comments"
        private const val EVENT_COLLECTION = "events"
        private const val EVENT_IS_EXPIRED = "isExpired"
        private const val POST_STATUS_COLLECTION = "postsStatus"
//        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_POST_TRACE = "updatePost"
    }
}