package com.example.finalproject.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.finalproject.components.CategoryButton
import com.example.finalproject.components.CategoryHashtag
import com.example.finalproject.components.CommentCard
import com.example.finalproject.components.DetailedView
import com.example.finalproject.components.EventDescription
import com.example.finalproject.components.EventDetailPhoto
import com.example.finalproject.models.Event
import com.example.finalproject.viewModels.EventViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.example.finalproject.components.EventTitle
import com.example.finalproject.components.QuickView
import com.example.finalproject.models.Comment
import com.example.finalproject.navigation.EventDetailsNavGraph
import com.example.finalproject.navigation.Graph
import com.example.finalproject.navigation.HomeNavGraph
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.darkerBackground
import com.example.finalproject.ui.theme.white
import java.util.Date

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
                        text = "Event",
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
                actions = {
                    IconButton(
                        onClick = {
                            /* TODO: Add code to navigate to 'report' screen */
                            navController.navigate(Graph.REPORT)

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Report"
                        )
                    }
                }
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
//        EventDetailsScreen(navController = NavHostController(LocalContext.current), eventViewModel = EventViewModel()
//        )
//    }
//}