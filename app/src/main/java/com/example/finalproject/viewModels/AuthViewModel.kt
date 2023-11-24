package com.example.finalproject.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.AuthRepository
import com.example.finalproject.data.model.Response
import com.example.finalproject.data.model.Response.Success
import com.example.finalproject.data.model.Response.Failure
import com.example.finalproject.data.model.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signInResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
}