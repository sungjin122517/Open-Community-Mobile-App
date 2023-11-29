package com.example.finalproject.data.service.module

import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.impl.AuthServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideAuthRepository(): AuthService = AuthServiceImpl(
        auth = Firebase.auth,
    )
}
