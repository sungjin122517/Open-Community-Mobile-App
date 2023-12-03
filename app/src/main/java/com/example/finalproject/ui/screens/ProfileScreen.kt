package com.example.finalproject.ui.screens

import android.content.Context
import android.widget.ProgressBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.finalproject.data.Preferences
import com.example.finalproject.data.model.Post
import com.example.finalproject.data.model.Response
import com.example.finalproject.data.model.User
import com.example.finalproject.ui.components.PostCard

import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.white
import com.example.finalproject.ui.viewModels.PostViewModel
import com.example.finalproject.ui.viewModels.ProfileViewModel
import com.example.finalproject.util.Utils.Companion.showMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserGreetings(username: String) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${username}'s Page",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = white
            )
        )
        Text(
            text = "HKUST",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = grey
            ),
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
        )
    }
}


@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
//    scaffoldState: ScaffoldState,
//    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

//    fun showRevokeAccessMessage() = coroutineScope.launch {
//        val result = scaffoldState.snackbarHostState.showSnackbar(
//            message = "You need to re-authenticate before revoking the access.",
//            actionLabel = "Sign out"
//        )
//        if (result == SnackbarResult.ActionPerformed) {
//            signOut()
//        }
//    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showMessage(context, "Your access has been revoked.")
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == "This operation is sensitive and requires recent authentication. Log in again before retrying this request.") {
                    signOut()
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    openPostDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedTabIndex = remember { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItem(title = "Post"),
        TabItem(title = "Save")
    )

    var pagerState = rememberPagerState { tabItems.size }

    var openMenu by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("")}

    Preferences.getUserName(context) {
        username = it ?: "User"
    }

    Surface(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = darkBackground,
                    titleContentColor = white,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ""
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    openMenu = !openMenu
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.MoreVert,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                },
                actions = {
                    DropdownMenu(
                        expanded = openMenu,
                        onDismissRequest = {
                            openMenu = !openMenu
                        },
                        modifier = Modifier
                            .background(darkBackground)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Sign Out",
                                    color = white
                                )
                            },
                            onClick = {
                                profileViewModel.signOut()
                                openMenu = !openMenu
                            },
                            colors = MenuDefaults.itemColors(darkBackground)
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Delete Account",
                                    color = white
                                )
                            },
                            onClick = {
                                profileViewModel.revokeAccess()
                                openMenu = !openMenu
                            }
                        )
                    }
                }
            )

            UserGreetings(username = username)

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                contentColor = white
            ) {
                val isSelected =
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        modifier = Modifier.background(darkBackground),
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = if (selectedTabIndex.value == index) FontWeight.Bold else FontWeight.Light,
                            )
                        },
//                        selectedContentColor = white
                    )
                }
            }
            when (selectedTabIndex.value) {
                0 -> {
                    MyPostList(navController = navController, viewModel = postViewModel, openPostDetailScreen = openPostDetailScreen)

                    //                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(darkBackground),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(
//                            text = "No Posts",
//                            color = white
//                        )
//                    }
                }
                1 -> {
                    SavedPostList(navController = navController, viewModel = postViewModel, openPostDetailScreen = openPostDetailScreen)
//                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(darkBackground),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(
//                            text = "No Saved",
//                            color = white
//                        )
//                    }
                }
            }
        }

    }

    RevokeAccess(
        signOut = {
            profileViewModel.signOut()
        }
    )
}

@Composable
fun MyPostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    openPostDetailScreen: (String) -> Unit
) {
//    val savedPostsIds by remember {
//        mutableStateOf(listOf<String>())
//    }

    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val myPostIds = user.value!!.myPostIds

    if (myPostIds.isNotEmpty()){
        LazyColumn(modifier = modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(myPostIds.reversed()) {postId ->
                val post = viewModel.fetchPost(postId).collectAsStateWithLifecycle(initialValue = Post())
                PostCard(
                    Modifier,
                    post = post.value?: Post(),
                    navController = navController,
                    isSaved = postId in user.value!!.savedPostIds,
                    viewModel::onSaveClicked,
                    openPostDetailScreen,
                    viewModel::incrementView,
                    viewModel::getTimeDifference,
                    false
                )

            }
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text("No Post Created Yet", modifier = Modifier)
            }
        }
    }

//    viewModel.savedPOstIds.li
}


@Composable
fun SavedPostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    openPostDetailScreen: (String) -> Unit
) {
//    val savedPostsIds by remember {
//        mutableStateOf(listOf<String>())
//    }

    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val savedPostIds = user.value!!.savedPostIds

    if (savedPostIds.isNotEmpty()){
        LazyColumn(modifier = modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(savedPostIds.reversed()) {postId ->
                val post = viewModel.fetchPost(postId).collectAsStateWithLifecycle(initialValue = Post())
                val isDeleted = post.value?.deleted ?: false
                if (post.value?.deleted == false) {
                    PostCard(
                        Modifier,
                        post = post.value?: Post(),
                        navController = navController,
                        isSaved = postId in user.value!!.savedPostIds,
                        viewModel::onSaveClicked,
                        openPostDetailScreen,
                        viewModel::incrementView,
                        viewModel::getTimeDifference,
                        false
                    )
                }


            }
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text("No Saved Post Yet", modifier = Modifier)
            }
        }
    }

//    viewModel.savedPOstIds.li
}




@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FinalProjectTheme {
//        ProfileScreen()
    }
}