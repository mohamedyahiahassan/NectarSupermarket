package com.example.nectarsupermarket.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.ConnectivityObserver
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont


@Composable
fun ShowSnackBar(visible:Boolean,modifier: Modifier) {

    val density = LocalDensity.current



    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInVertically (
            tween()
        ){


            with(density) { -10.dp.roundToPx() }

        },
        exit = slideOutVertically {

            with(density) { -10.dp.roundToPx() }
        }

    ) {


        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Red)
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically
            ){

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.baseline_cloud_off_24),
                    contentDescription = "No internet Connection Icon",
                    Modifier.size(35.dp))
                Text(
                    text = "Internet Connection Lost!",
                    color = Color.White,
                    fontFamily = gilroyFont,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(20.dp)

                )

                Spacer(modifier = Modifier.weight(1f))
            }

        }
    }

}