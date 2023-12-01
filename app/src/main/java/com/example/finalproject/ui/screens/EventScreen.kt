package com.example.finalproject.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.finalproject.ui.theme.white
import com.example.finalproject.ui.viewModels.EventViewModel

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
                contentColor = white
            ) {
                val isSelected =
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        modifier = Modifier.background(darkerBackground),
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
            .background(darkerBackground)
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
                    EventScreen(navController = navController, eventViewModel = EventViewModel())
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