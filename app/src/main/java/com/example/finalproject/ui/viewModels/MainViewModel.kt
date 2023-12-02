package com.example.finalproject.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: AuthService
): ViewModel() {
    init {
        getAuthState()
    }

    fun getAuthState() = service.getAuthState(viewModelScope)

//    fun sotoreUserPreference()

    val isEmailVerified get() = service.currentUser?.isEmailVerified ?: false
    val userID get() = service.currentUser?.uid.toString()
}