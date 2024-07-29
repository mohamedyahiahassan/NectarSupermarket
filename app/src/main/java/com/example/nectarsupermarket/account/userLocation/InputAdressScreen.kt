package com.example.nectarsupermarket.account.userLocation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
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
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.utils.WideButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp


@Composable
fun InputAddressContent(
    viewModel: AddressViewModel = viewModel(),
    address:String?=null,
    returnToAddressBook:()->Unit,
    openMaps:()->Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {

        if (address!=null){

            viewModel.addressAsStringLatLong.value = address

            viewModel.getReadableLocationFromStringAddressLatLang(context)
        }

        if (viewModel.selectedAddress.value!=null){

            viewModel.editAddress()

            viewModel.getReadableLocationSavedAddress(context)
        }
    }

    LaunchedEffect(key1 = viewModel.isAddingAddressSuccessFull.value) {

        if (viewModel.isAddingAddressSuccessFull.value == true){

            returnToAddressBook()
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(start = 20.sdp, end = 20.sdp),
        verticalArrangement = Arrangement.spacedBy(10.sdp)
    ) {

        Spacer(modifier = Modifier.weight(1f))

        OutlinedTextField(
            value = viewModel.label.value?:"",
            onValueChange = {
                viewModel.label.value = it
            },
            label = {
                Text(text = "Address Label")
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(1f))

        ElevatedCard(

            colors = CardDefaults.elevatedCardColors(Color.White),
            modifier = Modifier.height(100.sdp),
            onClick = {
                openMaps()

            }) {

            Row (
                modifier = Modifier.fillMaxSize(1f),
                verticalAlignment = Alignment.CenterVertically
            ){

                if (viewModel.readableAddress.value.isNullOrEmpty()){

                    Spacer(modifier = Modifier.weight(1f))

                    Image(Icons.Filled.LocationOn, contentDescription = "location change icon")

                    Text(
                        text = "Select Your Location",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.ssp,
                        color = Color.Black)

                    Spacer(modifier = Modifier.weight(1f))

                } else{

                    Image(Icons.Filled.LocationOn, contentDescription = "location change icon")

                    Text(
                        text = viewModel.readableAddress.value?:"",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.ssp,
                        color = Color.Black
                    )
                }

            }
        }

        OutlinedTextField(
            value = viewModel.city.value?:"",
            onValueChange = {
                viewModel.city.value = it
            },
            label = {
                Text(text = "City")
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(1f))

        OutlinedTextField(
            value = viewModel.district.value?:"",
            onValueChange = {
                viewModel.district.value = it
            },
            label = {

                Text(text = "District")
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(1f))

        OutlinedTextField(
            value = viewModel.street.value?:"",
            onValueChange = {
                viewModel.street.value = it
            },
            label = {

                Text(text = "Street")
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(1f))

        Row (
            modifier = Modifier.fillMaxWidth(1f)
        ){

            OutlinedTextField(
                value = viewModel.buildingNo.value?:"",
                onValueChange = {
                    viewModel.buildingNo.value = it
                },
                label = {

                    Text(text = "Building")
                },
                maxLines = 1,
                modifier = Modifier.weight(0.30f))

            Spacer(modifier = Modifier.weight(0.05f))

            OutlinedTextField(
                value = viewModel.floor.value?:"",
                onValueChange = {
                    viewModel.floor.value = it
                },
                label = {

                    Text(text = "Floor")
                },
                maxLines = 1,
                modifier = Modifier.weight(0.30f))

            Spacer(modifier = Modifier.weight(0.05f))

            OutlinedTextField(
                value = viewModel.aptNumber.value?:"",
                onValueChange = {
                    viewModel.aptNumber.value = it
                },
                label = {

                    Text(text = "Apt No.")
                },
                maxLines = 1,
                modifier = Modifier.weight(0.30f))

        }

        OutlinedTextField(
            value = viewModel.additionalAddressInfo.value?:"",
            onValueChange = {
                viewModel.additionalAddressInfo.value = it
            },
            label = {

                Text(text = "Additional Info")
            },
            modifier = Modifier.fillMaxWidth(1f))

        Spacer(modifier = Modifier.height(1.sdp))

        WideButton(buttonText = "Save Address") {

            viewModel.addNewAddress()

        }

        Spacer(modifier = Modifier.weight(1f))

    }
}