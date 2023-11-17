package com.example.finalproject.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.finalproject.components.CategoryButton
import com.example.finalproject.components.EventDate
import com.example.finalproject.components.EventHashTag
import com.example.finalproject.components.EventPhoto
import com.example.finalproject.components.EventTitle
import com.example.finalproject.components.NormalTextComponent
import com.example.finalproject.models.Event
import com.example.finalproject.navigation.Graph
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.viewModels.EventViewModel
import java.text.SimpleDateFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventScreen(navController: NavController, eventViewModel: EventViewModel) {
    var selectedTabIndex = remember { mutableIntStateOf(0) }
//    val scaffoldState = rememberScaffoldState()
    val tabItems = listOf(
        TabItem(title = "Formal"),
        TabItem(title = "Casual")
    )
    var pagerState = rememberPagerState {
        tabItems.size
    }
    val eventList = listOf<Event>(Event(), Event(), Event(), Event(), Event(), Event())

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
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
//                backgroundColor = MaterialTheme.colors.primary
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        text = {
                            Text(text = item.title)
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                ) {index ->

                
            }
            EventList(events = eventList, navController, eventViewModel)

//            Divider(
//                modifier = Modifier,
//                color = ApdiColors.lineGrey,
//                height = 0.dp,
//                thickness = 1.dp,
//                startIndent = 0.dp,
//                endIndent = 0.dp
//            )
        }
    }
}

data class TabItem(
    val title: String
)

@Composable
fun EventUI(event: Event, navController: NavController, eventViewModel: EventViewModel) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 12.dp, end = 6.dp)
            .width(176.dp)
            .background(darkBackground)
            .clickable {
//                navController.currentBackStackEntry?.savedStateHandle?.set(
//                    key = "event",
//                    value = event
//                )
                eventViewModel.addEvent(event)
                navController.navigate(Graph.EVENT_DETAILS)
            }
    ) {
        EventPhoto(event = event)
        CategoryButton(value = event.category)
        EventTitle(value = event.title)
        EventDate(event = event)
        EventHashTag(event = event)
    }
}

@Composable
fun EventList(events: List<Event>, navController: NavController, eventViewModel: EventViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(events.size) { count ->
            EventUI(event = events[count], navController, eventViewModel)
        }
    }
}

//@Preview
//@Composable
//fun EventScreenPreview() {
//    EventScreen()
//}

//@Preview
//@Composable
//fun EventUIPreview() {
//    EventUI(event = Event())
//}