/*
* This file contains all the code needed to get data from preference datastore
* (Not using SharedPreference since Google suggestion)
*
*/
package com.example.finalproject.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
//import com.example.finalproject.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.userPreferences by preferencesDataStore(name = "userPreferences")
val Context.savedPostIDs by preferencesDataStore(name = "savedPostIDs")

val USER_ID = stringPreferencesKey("userID")
//val SAVED_POST = Preferences.Key

fun getUserID(context: Context, callback: (String?) -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        val userID = context.userPreferences.data.map { preferences ->
            preferences[USER_ID]
        }.first()
        callback(userID)
    }
}


suspend fun storeSavedPostId(
    context: Context,
    postId: String,
    isSaved: Boolean = true
){
    val key = booleanPreferencesKey(postId)
    context.savedPostIDs.edit { preferences ->
        preferences[key] = isSaved
    }
}

suspend fun removeSavedPostId(
    context: Context,
    postId: String
){
    val key = booleanPreferencesKey(postId)
    context.savedPostIDs.edit { preferences ->
        preferences.remove(key)
    }
}

//fun checkSavedPost(
//    context: Context,
//    postId: String
//): Flow<Boolean> {
//    val key = booleanPreferencesKey(postId)
//    Log.d(TAG, "Paco: checkSavedPost $postId")
//    return context.savedPostIDs.data.map { preferences ->
//        preferences.contains(key)
//    }
//}


fun getSavedPost(
    context: Context,
    postId: String
): Flow<Preferences> {
    val key = booleanPreferencesKey(postId)
    Log.d(TAG, "Paco: checkSavedPost $postId")
    return context.savedPostIDs.data
}
