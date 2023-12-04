package com.example.finalproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.finalproject.ui.theme.green
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.red
import com.example.finalproject.ui.theme.white

@Composable
fun PopUpDialog(
    title: String,
    body: String,
    actionText: String = "Yes",
    onClose: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontSize = 16.sp,
            )
        },
        text = {
            Text(
                text = body,
                fontSize = 12.sp,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                content = {
                    Text(
                        text = actionText,
                        color = green,
                    )
                },
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onClose()
                },
                content = {
                    Text(
                        text = "Close",
                        color = red,
                    )
                },
            )
        },
//            modifier = Modifier
//                .background(grey)
    )

}

@Preview(showBackground = true)
@Composable
fun PopUpDialogPreview() {
//    MaterialTheme {
//        Surface(color = white) {
//            PopUpDialog(
//                title = "Title",
//                body = "Body",
//                actionText = "Yes",
//                closeText = "Cancel",
//                onDismiss = { /* Do something */ }
//            )
//        }
//    }
    PopUpDialog(
        title = "Title",
        body = "Body",
        actionText = "Yes",
        onClose = {},
        onDismiss = { /* Do something */ }
    )
}