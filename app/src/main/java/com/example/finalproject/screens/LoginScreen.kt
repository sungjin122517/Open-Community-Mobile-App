package com.example.finalproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.components.MyTextFieldComponent
import com.example.finalproject.components.NormalTextComponent
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.grey

@Composable
fun LoginScreen(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(28.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
            NormalTextComponent(value = "Login")
            MyTextFieldComponent(labelValue = "Email")
            MyTextFieldComponent(labelValue = "Password")
            // painterResource
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(grey)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Register",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}