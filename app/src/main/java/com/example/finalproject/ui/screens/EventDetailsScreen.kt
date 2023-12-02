package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finalproject.ui.components.CategoryButton
import com.example.finalproject.ui.components.CategoryHashtag
import com.example.finalproject.ui.components.DetailedView
import com.example.finalproject.ui.components.EventDescription
import com.example.finalproject.ui.components.EventDetailPhoto
import com.example.finalproject.ui.viewModels.EventViewModel
import com.example.finalproject.ui.components.EventTitle
import com.example.finalproject.ui.components.QuickView
import com.example.finalproject.ui.navigation.Graph
import com.example.finalproject.ui.theme.darkerBackground
import com.example.finalproject.ui.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(navController: NavHostController, eventViewModel: EventViewModel) {
//    val userInfoSnap = /* TODO: Add code to fetch user info snapshot */
//    val isEventSaved = /* TODO: Add code to determine if the event is saved */

    val event = eventViewModel.event

    Scaffold(
        contentColor = darkerBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = darkerBackground,
                    titleContentColor = white,
                ),
                title = {
                    Text(
                        text = "",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
                    }
                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            navController.navigate("report_graph/${event?.id}")
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Warning,
//                            contentDescription = "Report"
//                        )
//                    }
//                }
            )
        },
        content = {
//            EventDetailsNavGraph(navController = rememberNavController())
            Box(modifier = Modifier
                .fillMaxSize()
                .background(darkerBackground)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    EventDetailPhoto(event = event!!)
                    Column(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp)
                    ) {
                        CategoryButton(value = event.category)
                        EventTitle(value = event.title)
                        CategoryHashtag(event = event)
                        Spacer(modifier = Modifier.height(20.dp))
                        QuickView(event = event)
                        EventDescription(event = event)
                        DetailedView(event = event)
                    }
                    Spacer(modifier = Modifier
                        .height(20.dp)
                    )
                    Row(
                        modifier = Modifier
//                            .align(Alignment.BottomStart)
                            .padding(start = 20.dp, bottom = 45.dp)
                    ) {
                        Button(
                            onClick = {
                                /* TODO: Add code to launch the event registration URL */
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.76f)
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
//                            colors = Color(hexStringToColor(buttonColor(event.category)))
                        ) {
                            Text(
                                text = "Register",
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W500
                            )
                        }
//                        EventSaveButton(
//                            controller = /* TODO: Add the event controller */,
//                            currentUser = /* TODO: Add the current user */,
//                            saved = isEventSaved,
//                            modifier = Modifier.padding(start = 18.dp)
//                        )
                    }
                }
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun EventDetailsPagePreview() {
//    FinalProjectTheme(darkTheme = true) {
//        EventDetailsScreen(navController = rememberNavController(), eventViewModel = EventViewModel()
//        )
//    }
//}