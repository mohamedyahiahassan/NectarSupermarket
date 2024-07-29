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
import coil.compose.AsyncImage
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp
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
            .padding(20.sdp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                text = "# ${viewModel.selectedOrder.value?.orderName}",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.ssp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.weight(1f))

            if (viewModel.selectedOrder.value?.orderStatus != 4) {


                OutlinedButton(
                    shape = RoundedCornerShape(5.sdp),
                    border = BorderStroke(1.sdp, Color.Red),
                    contentPadding = PaddingValues(
                        start = 15.sdp,
                        end = 15.sdp,
                        top = 7.sdp,
                        bottom = 7.sdp
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
                        fontSize = 14.ssp,
                        color = Color.Red,
                    )

                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "track order",
                        tint = Color.Red,
                        modifier = Modifier.size(18.sdp)
                    )
                }


            } else {

                Text(
                    text = "Delivered",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.ssp,
                    color = greenPrimary,
                )
            }


        }


        Text(
            text = viewModel.selectedOrder.value?.created?.toDate().toString().substringBefore("G")
                .substringBeforeLast(":"),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.ssp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(20.sdp))

        Text(
            text = "DeliveredTo",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.ssp,
            color = Color.Black,
        )

        Text(
            text = viewModel.readableAddress,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.ssp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(20.sdp))

        HorizontalDivider(thickness = 1.sdp, color = greyBorder)

        Spacer(modifier = Modifier.height(20.sdp))

        Column(
            modifier = Modifier
                .fillMaxWidth(1f),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
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
                        fontSize = 16.ssp,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.width(20.sdp))

                    AsyncImage(
                        model = it[0],
                        contentDescription = "product image",
                        modifier = Modifier.size(40.sdp)
                    )

                    Spacer(modifier = Modifier.width(20.sdp))

                    Column(
                    ) {

                        Text(
                            text = it[1],
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.ssp,
                            color = Color.Black,
                        )

                        Text(
                            text = it[2],
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.ssp,
                            color = greyLabels,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "LE ${it[3]}",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.ssp,
                        color = Color.Black,
                    )


                }
            }
        }


        Spacer(modifier = Modifier.height(20.sdp))

        HorizontalDivider(thickness = 1.sdp, color = greyBorder)

        Spacer(modifier = Modifier.height(20.sdp))

        Row(
            modifier = Modifier.fillMaxWidth(1f)
        ) {

            Text(
                text = "Total:",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.ssp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "LE ${viewModel.selectedOrder.value?.totalPrice}",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.ssp,
                color = Color.Black,
            )
        }
    }
}

