package com.example.finalproject.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.model.Response
import com.example.finalproject.data.model.Response.Success
import com.example.finalproject.data.model.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthService
): ViewModel() {
    var signUpResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
//    var username by mutableStateOf<String?>(null)
//        private set
//    var email by mutableStateOf<String?>(null)
//        private set
//
//    fun addInfo(newUsername: String, newEmail: String) {
//        username = newUsername
//        email = newEmail
//    }


    fun signUpWithEmailAndPassword(email: String, password: String, username: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password, username)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
}