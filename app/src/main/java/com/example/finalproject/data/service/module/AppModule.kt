package com.example.finalproject.data.service.module

import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostsService
import com.example.finalproject.data.service.UserService
import com.example.finalproject.data.service.impl.AuthServiceImpl
import com.example.finalproject.data.service.impl.PostsServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAuthService(): AuthService = AuthServiceImpl(
        auth = Firebase.auth,
    )

    @Provides
    fun provideStorageService(): PostsService = PostsServiceImpl(
        firestore = Firebase.firestore,
        auth = AuthServiceImpl(
            auth = Firebase.auth,
        )
    )

//    @Provides
//    fun provideUserService(): UserService = UserService(
//        firestore = Firebase.firestore
//    )
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class ServiceModule {
//
//    abstract provide
//}