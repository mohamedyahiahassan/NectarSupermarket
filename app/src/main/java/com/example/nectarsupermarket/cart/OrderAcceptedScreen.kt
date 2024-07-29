package com.example.nectarsupermarket.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.WideButton
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp

@Composable
fun OrderAcceptedScreen( navigateToHome:()->Unit,trackOrder:()->Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(start = 20.sdp, end = 20.sdp)
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.order_accepted_image),
            contentDescription = "order accepted image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(0.7f))

        Text(
            text = "Your Order has been\naccepted",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.ssp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.sdp))

        Text(
            text = "Your items has been placed and is on\nit's way to being processed",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.ssp,
            color = greyLabels,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))


        WideButton(buttonText = "Track Order") {

            trackOrder()

        }

        TextButton(
            onClick = {

                navigateToHome()


            }) {

            Text(
                text = "Back to home",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Medium,
                fontSize = 18.ssp,
                color = Color.Black,
            )

        }

        Spacer(modifier = Modifier.weight(0.1f))

    }

}