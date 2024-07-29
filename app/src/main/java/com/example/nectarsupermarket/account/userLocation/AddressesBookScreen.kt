package com.example.nectarsupermarket.account.userLocation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.contract.User
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.utils.WideButton
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp


@Composable
fun AddressesBookContent(
    viewModel: AddressViewModel = viewModel(),
    addNewOrEditAddress:()->Unit
) {



    LaunchedEffect(key1 = Unit) {

        viewModel.listOfAddresses.clear()

        viewModel.getAddresses()
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(1f)
            .padding(start = 20.sdp, end = 20.sdp)

    ) {
        
        if (User.appUser==null){
            
            Spacer(modifier = Modifier.height(30.sdp))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.sdp),

            ) {

            if (viewModel.listOfAddresses.isNotEmpty()) {

                items(viewModel.listOfAddresses) { address ->


                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        colors = CardDefaults.cardColors(Color.White),
                        border = BorderStroke(1.sdp, greyBorder),
                        onClick = {

                            viewModel.selectedAddress.value = address
                            addNewOrEditAddress()


                        }) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(20.sdp)
                        ) {

                            Text(
                                text = address.label ?: "",
                                fontFamily = gilroyFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.ssp,
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.Start)
                            )

                            Spacer(modifier = Modifier.height(20.sdp))


                            Text(
                                text = address.buildingNo + "," + address.street,
                                fontFamily = gilroyFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.ssp,
                                color = Color.Black,
                            )

                            Spacer(modifier = Modifier.height(20.sdp))

                            Text(
                                text = address.district + "," + address.city,
                                fontFamily = gilroyFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.ssp,
                                color = Color.Black,
                            )
                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))


        WideButton(buttonText = "Add New Address") {

            addNewOrEditAddress()

        }

        Spacer(modifier = Modifier.height(20.sdp))
    }
}



