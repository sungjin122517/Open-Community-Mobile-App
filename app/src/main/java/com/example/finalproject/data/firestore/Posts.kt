package com.example.finalproject.data.firestore

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

fun fetchPost() =  flow<Map<String, Any>?> {
    val db = com.google.firebase.Firebase.firestore
    val postRef = db.collection("posts").document("TEST_POST_ID")
    val postStatusRef = db.collection("postsStatus").document("TEST_POST_ID")

//    var postData: Map<String, Any>? = null
//    var postStatusData: Map<String, Any>? = null

//    postRef.get()
//        .addOnSuccessListener { post ->
//            if (post != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${post.data}")
////                callback(post.data)
//                 emit(post.data)
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d(TAG, "get failed with ", exception)
//        }

//    postStatusRef.get()
//        .addOnSuccessListener { postStatus ->
//            if (postStatus != null) {
//                Log.d(TAG, "DocumentSnapshot data: ${postStatus.data}")
//                flow<Map<String, Any>?> { emit(postStatus.data) }
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d(TAG, "get failed with ", exception)
//        }
}

fun fetchData() {
    val data = fetchPost()

    Log.d(TAG, "postData: $data")
}