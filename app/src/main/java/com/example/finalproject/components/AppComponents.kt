package com.example.finalproject.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.ui.theme.white

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            fontStyle = FontStyle.Normal,
            color = white
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        textAlign = TextAlign.Center,
        maxLines = 2
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(labelValue: String) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        value = textValue.value,
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = colorResource(id = R.color.colorPrimary)
//        )
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it
        },
//        leadingIcon = {
//            Icon(painter = painterResource(id = R.drawable.))
//        }
    )
}