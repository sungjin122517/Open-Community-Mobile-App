package com.example.finalproject.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Event : BottomBarScreen(
        route = "EVENT",
        title = "Event",
        icon = Icons.Default.DateRange
    )

    object Post : BottomBarScreen(
        route = "POST",
        title = "Community",
        icon = Icons.Default.People
    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )

}
