package com.example.finalproject.data

import android.util.Log
import com.example.finalproject.data.model.Response
import com.example.finalproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String, username: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
        val userId = auth.currentUser?.uid!!
//        val user = User(name = username, uid = userId)
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
//        userRef.set(user)
        userRef.set(
            mapOf(
                "email" to email,
                "username" to username,
            )
        )
//                }
//            }
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess() = try {
        auth.currentUser?.delete()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

//    override fun firebaseCreateUser(email: String, username: String): Response<Boolean> = try{
//
//    }
}