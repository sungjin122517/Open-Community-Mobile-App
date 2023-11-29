/*
* This file contains all the code needed to get data from preference datastore
* (Not using SharedPreference since Google suggestion)
*
*/
package com.example.finalproject.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.finalproject.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val USER_ID = stringPreferencesKey("userID")

fun getUserID(context: Context, callback: (String?) -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        val userID = context.dataStore.data.map { preferences ->
            preferences[USER_ID]
        }.first()
        callback(userID)
    }
}