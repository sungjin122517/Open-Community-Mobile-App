package com.example.finalproject.data.service.module

import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.CommunityService
import com.example.finalproject.data.service.impl.AuthServiceImpl
import com.example.finalproject.data.service.impl.CommunityServiceImpl
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
    fun provideStorageService(): CommunityService = CommunityServiceImpl(
        firestore = Firebase.firestore,
        auth = AuthServiceImpl(
            auth = Firebase.auth,
        )
    )

//    @Provides
//    fun providePostService(): PostService = PostServiceImpl(
//        Firebase.firestore,
//        auth = AuthServiceImpl(
//            auth = Firebase.auth,
//        ),
//    )
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class ServiceModule {
//
//    abstract provide
//}