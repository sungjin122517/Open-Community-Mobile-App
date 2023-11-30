package com.example.finalproject.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.data.service.PostsService
import com.example.finalproject.data.service.impl.PostsServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val service: PostsService
): ViewModel() {
    var posts = service.posts

//    fun onFetch() {
//        contin
//        Log.d(TAG, "DocumentSnapshot data: ${posts.collect()}")
//    }
}

//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ServiceModule {
//    @Binds
//    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//
//    …
//}
