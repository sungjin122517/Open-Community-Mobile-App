package com.example.finalproject.ui.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.model.Response
import com.example.finalproject.data.model.Response.Success
import com.example.finalproject.data.model.Response.Loading
import com.example.finalproject.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val service: AuthService
): ViewModel() {
    var signInResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String, context: Context) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = service.firebaseSignInWithEmailAndPassword(email, password)

        if (signInResponse is Response.Failure) {
            Utils.showMessage(context, "Wrong email or password. Please try again.")
        } else if (signInResponse is Success) {
            Utils.showMessage(context, "Login success")
        }
    }
}