package com.example.finalproject.data.service.module

import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.PostService
import com.example.finalproject.data.service.EventService
import com.example.finalproject.data.service.UserService
import com.example.finalproject.data.service.impl.AuthServiceImpl
import com.example.finalproject.data.service.impl.PostServiceImpl
import com.example.finalproject.data.service.impl.EventServiceImpl
import com.example.finalproject.data.service.impl.UserServiceImpl
import com.google.firebase.auth.ktx.auth
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
    fun provideUserService(): UserService = UserServiceImpl(
        firestore = Firebase.firestore,
        auth = AuthServiceImpl(
            auth = Firebase.auth,
        )
    )

    @Provides
    fun provideStorageService(): PostService = PostServiceImpl(
        firestore = Firebase.firestore
    )

    @Provides
    fun provideEventService(): EventService = EventServiceImpl(
        Firebase.firestore
    )
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class ServiceModule {
//
//    abstract provide
//}