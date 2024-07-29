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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyBorder


@Composable
fun OrdersScreenContent(
    viewModel: OrderViewModel = viewModel(),
    trackOrder:(orderNumber:String,orderStatus:Int)->Unit,
    navigateToOrderDetails:()->Unit){


    LaunchedEffect(key1 = Unit) {

        viewModel.getOrders()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(1f)
            .padding(start = 20.sdp, end = 20.sdp)
    ) {


        items(viewModel.listOfOrders){order->


            Card (
                modifier = Modifier
                    .fillMaxWidth(1f),
                colors = CardDefaults.cardColors(Color.White),
                border = BorderStroke(1.sdp, greyBorder),
                onClick = {

                    viewModel.selectedOrder.value = order

                    navigateToOrderDetails()


                }


            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(20.sdp)
                ) {

                    Text(
                        text = order.created?.toDate().toString().substringBefore("G")
                            .substringBeforeLast(":"),
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.ssp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(20.sdp))

                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {

                        Text(
                            text = if (order.listOfOrderItems?.size == 1) "${order.listOfOrderItems?.size} item" else "${order.listOfOrderItems?.size} items",
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.ssp,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "LE ${order.totalPrice}",
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.ssp,
                            color = Color.Black,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.sdp))

                    HorizontalDivider(thickness = 1.sdp, color = greyBorder)

                    Spacer(modifier = Modifier.height(20.sdp))


                    if (order.orderStatus == 4) {

                        Text(
                            text = "Delivered",
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.ssp,
                            color = Color.Green,
                        )
                    } else {

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

                                trackOrder(order.orderName.toString(),order.orderStatus?:1)
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
                    }
                }


            }
            }
        }


    }

