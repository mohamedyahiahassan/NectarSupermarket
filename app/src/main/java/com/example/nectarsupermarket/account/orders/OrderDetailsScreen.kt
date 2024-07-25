package com.example.nectarsupermarket.account.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.ui.theme.greyLabels


@Composable
fun OrderDetailsContent(viewModel: OrderViewModel, trackOrder:(orderNumber:String, orderStatus:Int)->Unit){


    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {

        viewModel.getOrderAddress(context)


        viewModel.getListOfProductsInSelectedOrder()
    }


    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                text = "# ${viewModel.selectedOrder.value?.orderName}",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.weight(1f))

            if (viewModel.selectedOrder.value?.orderStatus != 4) {


                OutlinedButton(
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, Color.Red),
                    contentPadding = PaddingValues(
                        start = 15.dp,
                        end = 15.dp,
                        top = 7.dp,
                        bottom = 7.dp
                    ),

                    onClick = {

                        trackOrder(
                            viewModel.selectedOrder.value?.orderName.toString(),
                            viewModel.selectedOrder.value?.orderStatus ?: 1
                        )
                    })
                {

                    Text(
                        text = "Track Order",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Red,
                    )

                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "track order",
                        tint = Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                }


            } else {

                Text(
                    text = "Delivered",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = greenPrimary,
                )
            }


        }


        Text(
            text = viewModel.selectedOrder.value?.created?.toDate().toString().substringBefore("G")
                .substringBeforeLast(":"),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "DeliveredTo",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black,
        )

        Text(
            text = viewModel.readableAddress,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(thickness = 1.dp, color = greyBorder)

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            viewModel.listOfProductsInSelectedOrder?.forEach {

                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${it[4]}x",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    AsyncImage(
                        model = it[0],
                        contentDescription = "product image",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(
                    ) {

                        Text(
                            text = it[1],
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.Black,
                        )

                        Text(
                            text = it[2],
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = greyLabels,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "LE ${it[3]}",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )


                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(thickness = 1.dp, color = greyBorder)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(1f)
        ) {

            Text(
                text = "Total:",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "LE ${viewModel.selectedOrder.value?.totalPrice}",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
            )
        }
    }
}

