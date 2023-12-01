package com.example.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.data.USER_ID
import com.example.finalproject.data.savedPostIDs
import com.example.finalproject.data.service.module.AppModule
import com.example.finalproject.data.userPreferences
import com.example.finalproject.ui.navigation.Graph
import com.example.finalproject.ui.navigation.RootNavigationGraph
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.viewModels.MainViewModel
import com.example.finalproject.ui.viewModels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            val postsVM = PostsViewModel(AppModule.provideStorageService())
//            LaunchedEffect(postsVM) {
//                val postList = postsVM.posts.onEach {
//                    for( post in it) {
//                        Log.d(TAG, "posts: $post")
//                    }
//                }
//            }
            navController = rememberNavController()
            FinalProjectTheme(darkTheme = true) {
                RootNavigationGraph(navController)
            }
            AuthState()
        }

    }

    @SuppressLint("CommitPrefEdits", "CoroutineCreationDuringComposition")
    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                val userID = viewModel.userID

                runBlocking {
                    applicationContext.userPreferences.edit { preferences ->
                        preferences[USER_ID] = userID
                    }
                }
                Log.d(TAG, "useID: ${viewModel.userID}")
                NavigateToHomeScreen()
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(Graph.AUTHENTICATION) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToHomeScreen() = navController.navigate(Graph.HOME) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() = navController.navigate(Graph.VERIFY_EMAIL) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme {
//        Greeting("Android")
    }
}