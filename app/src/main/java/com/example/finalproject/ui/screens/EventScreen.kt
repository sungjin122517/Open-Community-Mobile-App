package com.example.finalproject.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.components.CategoryButton
import com.example.finalproject.ui.components.EventDate
import com.example.finalproject.ui.components.EventHashTag
import com.example.finalproject.ui.components.EventPhoto
import com.example.finalproject.ui.components.EventTitle
import com.example.finalproject.data.model.Event
import com.example.finalproject.ui.navigation.Graph
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.darkerBackground
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.white
import com.example.finalproject.ui.viewModels.EventViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventScreen(navController: NavController, eventViewModel: EventViewModel) {
//    val eventList = eventViewModel.fetchEvents() ?: mutableListOf<Event>()
    val eventList = eventViewModel.eventList
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
            EventHeading()
            Spacer(     // horizontal divisor
                modifier = Modifier.imePadding()
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            EventList(events = eventList.toList(), navController, eventViewModel)
        }
    }
}

@Composable
fun EventHeading() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "University Event",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = white
            )
        )
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

@Preview
@Composable
fun EventScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomBar(navController = navController) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                ){
                    EventScreen(navController = navController, eventViewModel = viewModel())
                }
            }
        )
    }
}

//@Preview
//@Composable
//fun EventUIPreview() {
//    EventUI(event = Event())
//}