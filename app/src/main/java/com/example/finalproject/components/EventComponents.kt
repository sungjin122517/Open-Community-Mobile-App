package com.example.finalproject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.finalproject.models.Event
import com.example.finalproject.ui.theme.green
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.white
import java.text.SimpleDateFormat

@Composable
fun EventTitle(value: String) {
    Box(modifier = Modifier) {
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.titleSmall
                .copy(
                    fontFamily = FontFamily.SansSerif,
                    color = white,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
        )
    }
}

@Composable
fun EventPhoto(event: Event) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .height(112.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageURL)
                    .build()
            ),
            contentDescription = "Event Photo",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.33f)
        )
    }
}

@Composable
fun CategoryButton(value: String) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .border(
                    1.5.dp,
                    green
                )
                .background(
                    color = Color.Transparent,
                )
                .padding(horizontal = 5.dp)
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                color = green,
                style = TextStyle(
                    fontSize = 13.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {

            },
            modifier = Modifier.padding(start = 8.dp),
            content = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Report",
                    tint = white
                )
            }
        )
    }
}

@Composable
fun EventDate(event: Event) {
    Text(
        text = SimpleDateFormat("yyyy-MM-dd").format(event.eventTime),
        style = TextStyle(
//            fontFamily = FontFamily("Outfit"),
            color = grey,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = Modifier.padding(top = 10.dp)
    )
}

@Composable
fun EventHashTag(event: Event) {
    val tags = event.tag.joinToString(" #", prefix = "#")

    Text(
        text = tags,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
//            fontFamily = FontFamily("Outfit"),
            color = grey,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = Modifier
            .width(168.dp)
            .padding(top = 10.dp)
    )
}

@Composable
fun EventDetailPhoto(event: Event) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageURL)
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SingleHashtag(event: Event, index: Int) {
    val backgroundColor = Color.Transparent

    Box(
        modifier = Modifier
            .padding(top = 14.dp, end = 10.dp)
            .height(22.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = "#${event.tag[index]}",
            color = white,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 9.dp, top = 5.dp, end = 9.dp)
        )
    }
}

@Composable
fun CategoryHashtag(event: Event) {
    Row {
        event.tag.forEach { tag ->
            SingleHashtag(event = event, index = event.tag.indexOf(tag))
        }
    }
}

@Composable
fun QuickView(event: Event) {
    val backgroundColor = Color.Transparent

    Column(
        modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .padding(start = 17.dp, top = 22.dp, end = 17.dp, bottom = 22.dp)
    ) {
        Text(
            text = "Quick View",
            fontSize = 16.sp,
            color = white,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Column {
                Text(
                    text = "Date",
                    fontSize = 14.sp,
                    color = grey
                )
                Text(
                    text = SimpleDateFormat("dd MMM y").format(event.eventTime),
                    fontSize = 16.sp,
                    color = white,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
            Column {
                Text(
                    text = "Time",
                    fontSize = 14.sp,
                    color = grey
                )
                Text(
                    text = SimpleDateFormat("HH:mm").format(event.eventTime),
                    fontSize = 16.sp,
                    color = white,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EventDescription(event: Event) {
    Row(
        modifier = Modifier
            .padding(
                top = 18.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = event.description,
            fontSize = 16.sp,
            color = white,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DetailedView(event: Event) {
    val backgroundColor = Color.Transparent

    Column(
        modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .padding(start = 17.dp, top = 22.dp, end = 17.dp, bottom = 22.dp)
    ) {
        Text(
            text = "Detailed View",
            fontSize = 16.sp,
            color = white,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Date",
                fontSize = 14.sp,
                color = grey,
                modifier = Modifier.width(80.dp)
            )
            Text(
                text = SimpleDateFormat("dd MMM y").format(event.eventTime),
                fontSize = 16.sp,
                color = white,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Time",
                fontSize = 14.sp,
                color = grey,
                modifier = Modifier.width(80.dp)
            )
            Text(
                text = SimpleDateFormat("HH:mm").format(event.eventTime),
                fontSize = 16.sp,
                color = white,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Language",
                fontSize = 14.sp,
                color = grey,
                modifier = Modifier.width(80.dp)
            )
            Text(
                text = event.language.joinToString(", "),
                fontSize = 16.sp,
                color = white,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Location",
                fontSize = 14.sp,
                color = grey,
                modifier = Modifier.width(80.dp)
            )
            Text(
                text = event.location,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = white,
                modifier = Modifier.weight(1f)
            )
        }
    }
}