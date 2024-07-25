package com.example.nectarsupermarket.account.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels


@Composable
fun OrderTrackingContent(viewModel: OrderViewModel, orderNumber:String, orderStatus:Int){

    LaunchedEffect(key1 = Unit) {


        viewModel.orderNumber = orderNumber

        viewModel.orderStatus = orderStatus

        viewModel.updateVisualOrderStatus()


    }


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)

    ){

        Text(
            text = "Tracking Order:",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = greenPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "#${viewModel.orderNumber}",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = greenPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(40.dp))


       for (i in 0..<viewModel.listOfOrderStatusItems.size){

           if (i==3){

               OrderStatusItem(
                   title = viewModel.listOfOrderStatusItems[i].title,
                   desc =viewModel.listOfOrderStatusItems[i].desc ,
                   modifier =viewModel.listOfOrderStatusItems[i].modifier,
                   showDivider = false,
                   dividerColor = viewModel.listOfOrderStatusItems[i].dividerColor)

           } else {

               OrderStatusItem(
                   title = viewModel.listOfOrderStatusItems[i].title,
                   desc =viewModel.listOfOrderStatusItems[i].desc ,
                   modifier =viewModel.listOfOrderStatusItems[i].modifier,
                   showDivider = true,
                   dividerColor = viewModel.listOfOrderStatusItems[i].dividerColor)
           }

       }



    }




}

@Composable
fun OrderStatusItem(title:String,desc:String,modifier: Modifier,showDivider:Boolean,dividerColor:Color){

    Text(
        text = title,
        fontFamily = gilroyFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = desc,
        fontFamily = gilroyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = greyLabels,
        textAlign = TextAlign.Center,
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(20.dp))

    if (showDivider==true){

        VerticalDivider(Modifier.height(60.dp),3.dp,dividerColor)

        Spacer(modifier = Modifier.height(20.dp))
    }




}
