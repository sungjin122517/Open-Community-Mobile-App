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
import androidx.compose.foundation.layout.padding
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
import com.example.finalproject.data.model.Response
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.white
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

//@Composable
//fun showDialog(context: Context, message: String, onDismiss: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text(text = "Revoke Access") },
//        text = { Text(text = message) },
//        confirmButton = {
//            TextButton(onClick = onDismiss) {
//                Text(text = "Cancel")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = { onDismiss() }) {
//                Text(text = "Sign out")
//            }
//        }
//    )
//}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var selectedTabIndex = remember { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItem(title = "Post"),
        TabItem(title = "Save")
    )

    var pagerState = rememberPagerState { tabItems.size }

    var openMenu by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("")}

    var currentUserId = viewModel.getUserId
    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            val userRef = FirebaseFirestore.getInstance().collection("users").document(currentUserId)
            userRef.get().addOnSuccessListener { documentSnapshot ->
                username = documentSnapshot.getString("username")!!
            }
        } else {
            username = "User"
        }
    }

    Surface(
        modifier = Modifier
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
                                viewModel.signOut()
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
                                viewModel.revokeAccess()
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
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(darkBackground),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "No Posts",
                            color = white
                        )
                    }
                }
                1 -> {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(darkBackground),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "No Saved",
                            color = white
                        )
                    }
                }
            }
        }

    }

    RevokeAccess(
        signOut = {
            viewModel.signOut()
        }
    )
}



@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FinalProjectTheme {
        ProfileScreen()
    }
}