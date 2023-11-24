package com.example.finalproject.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.AuthRepository
import com.example.finalproject.data.model.Response
import com.example.finalproject.data.model.Response.Failure
import com.example.finalproject.data.model.Response.Success
import com.example.finalproject.data.model.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
//    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Success(false))
//        private set
    var reloadUserResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = repo.reloadFirebaseUser()
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false
    val getUserId get() = repo.currentUser?.uid

    fun signOut() = repo.signOut()

//    fun revokeAccess() = viewModelScope.launch {
//        revokeAccessResponse = Loading
//        revokeAccessResponse = repo.revokeAccess()
//    }
}