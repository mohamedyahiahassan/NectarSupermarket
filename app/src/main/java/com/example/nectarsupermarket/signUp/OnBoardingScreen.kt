package com.example.nectarsupermarket.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyWhite

@Composable
fun OnBoarding(onGetStartedClicked:()->Unit){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.onboarding_background),
                contentScale = ContentScale.Crop
            )
            .fillMaxSize(1f)

    ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.55f))

        Image(
            painter = painterResource(id = R.drawable.logo_white),
            contentDescription = "onboarding_logo",
            modifier = Modifier.fillMaxWidth(0.12f),
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Welcome" + "\n" + "to our store",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 48.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 48.sp
        )

        Text(
            text = "Get your groceries in as fast as one hour",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = greyWhite
        )

        Spacer(modifier = Modifier.height(20.dp))
        
        Button(
            onClick = {
                onGetStartedClicked()
            },
            shape = RoundedCornerShape(19.dp),
            colors = ButtonDefaults.buttonColors(greenPrimary),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.35f)
        ) {

            Text(
                text = "Get Started",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )
            
        }

    }
}