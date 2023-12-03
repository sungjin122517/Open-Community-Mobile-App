package com.example.finalproject.data.service.impl

//import com.example.finalproject.data.model.Post
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.tracing.Trace
import com.example.finalproject.data.model.Comment
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.PostStatus
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): PostService {
    override val posts: Flow<List<Post>>
        get() {
            Log.d(ContentValues.TAG, "Paco: fetch posts")
            return firestore.collection(POST_COLLECTION)
//                .whereEqualTo("deleted", false)
                .orderBy("time", Query.Direction.DESCENDING).dataObjects<Post>()
        }

    override fun getPost(postId: String): Flow<Post?> =
        firestore.collection(POST_COLLECTION).document(postId).dataObjects<Post>()

    override suspend fun getPostStatus(postId: String): Flow<PostStatus?> {
        Log.d(ContentValues.TAG, "Paco: get post status")
        return firestore.collection(POST_STATUS_COLLECTION).document(postId).dataObjects<PostStatus>()
    }

    override fun getPostComment(postId: String): Flow<List<Comment>> {
        Log.d(ContentValues.TAG, "Paco: get post status")
        return firestore.collection(POST_COLLECTION).document(postId)
            .collection(COMMENT_COLLECTION)
            .orderBy("time", Query.Direction.DESCENDING).dataObjects<Comment>()
    }

    override suspend fun updatePostField(postId: String, updateMap: Map<String, Any>) {
        Trace.beginSection(UPDATE_POST_TRACE)
        firestore.collection(POST_COLLECTION).document(postId).update(updateMap).await()
        Trace.endSection()
    }

    override suspend fun createPost(post: Post): Task<DocumentReference> {
        return firestore.collection(POST_COLLECTION).add(post)

    }

    override suspend fun addComment(post: Post, commentMap: Map<String, Any>) {
        firestore.collection(POST_COLLECTION).document(post.id)
            .collection(COMMENT_COLLECTION).add(commentMap).addOnSuccessListener {
                firestore.collection(POST_COLLECTION).document(post.id).update(mapOf(
                    "commentCount" to post.commentCount + 1
                ))
            }

    }

    override suspend fun addReport(postId: String, reportMap: Map<String, Any>) {
        firestore.collection(POST_COLLECTION).document(postId)
            .collection(REPORT_COLLECTION).add(reportMap)
    }


    override suspend fun delete(postId: String) {
//        firestore.collection(POST_COLLECTION).document(postId)
//            .update(mapOf("deleted" to true)).await()
        firestore.collection(POST_COLLECTION).document(postId).delete().await()
    }


    companion object {
        private const val POST_COLLECTION = "posts"
        private const val COMMENT_COLLECTION = "comments"
        private const val REPORT_COLLECTION = "reports"
        private const val POST_STATUS_COLLECTION = "postsStatus"
        private const val UPDATE_POST_TRACE = "updatePost"
    }
}

//class PostServiceTestImpl @Inject constructor(): PostService {
//    override val posts: Flow<List<Post>>
//        get() = flowOf(listOf<Post>())
////    override val user: Flow<User?>
////        get() = flowOf(User())
//
//    override fun getPost(postId: String): Flow<Post?> {
//        return flowOf(Post(
//            id = "TEST_POST_ID_00", title = "this is title", content = "HI"
//        ))
//    }
//
//    override suspend fun getPostStatus(postId: String): Flow<PostStatus?> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getPostComment(postId: String): Flow<List<Comment>> {
//        return flowOf(listOf<Comment>(Comment(content = "Comment")))
//    }
//
//    override suspend fun createPost(post: Post) {
//        TODO("Not yet implemented")
//    }
//
//
//    override suspend fun updatePostField(postId: String, updateMap: Map<String, Any>) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun addComment(post: Post, commentMap: Map<String, Any>) {
//        Log.d(TAG, "Paco: added comment = commentMap")
//    }
//
//    override suspend fun addReport(postId: String, reportMap: Map<String, Any>) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun updateUserField(updateMap: Map<String, Any>) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun delete(post: String) {
//        TODO("Not yet implemented")
//    }
//
//}