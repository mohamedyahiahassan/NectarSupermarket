package com.example.nectarsupermarket.utils

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary

@Composable
fun WideButton(buttonText:String,onButtonClick:()->Unit){

    Button(
        onClick = {

            onButtonClick()
        },
        shape = RoundedCornerShape(19.sdp),
        colors = ButtonDefaults.buttonColors(greenPrimary),
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(65.sdp)
    ) {

        Text(
            text = buttonText,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.ssp,
            color = Color.White
        )

    }
}