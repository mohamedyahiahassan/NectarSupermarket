package com.example.nectarsupermarket.account.userDetailsInput

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailDefaults.windowInsets
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.domain.contract.User
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.LoadingDialog
import com.example.nectarsupermarket.utils.LoginTextField
import com.example.nectarsupermarket.utils.WideButton
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsContent(
    viewModel: UserDetailsViewModel = viewModel(),
    openAddressBook:()->Unit,
    navigateToApp:()->Unit){

    val expanded = remember {

        mutableStateOf(false)
    }

    val selectLocationText = remember {

        mutableStateOf(User.appUser?.selectedAddress)
    }


    LaunchedEffect(key1 = Unit) {

        viewModel.getAddresses()


       if (User.appUser?.latitude!=null){

            viewModel.addressAsStringLatLong.value = "${User.appUser!!.latitude.toString()},${User.appUser!!.longitude.toString()}"

            viewModel.username.value = User.appUser!!.firstName.toString()

            viewModel.mobileNumber.value = User.appUser!!.phoneNumber.toString()

            viewModel.imageUri.value = Uri.parse(User.appUser!!.userImage)

        }
    }

    LaunchedEffect(key1 = viewModel.isSubmittingUserDataComplete.value) {

        if (viewModel.isSubmittingUserDataComplete.value == true){

            navigateToApp()
        }

    }

    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->

            viewModel.imageUri.value = uri

            viewModel.uploadUserImage()

        })
    
    LoadingDialog(isLoading = viewModel.isLoading)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .padding(
                start = 20.sdp,
                end = 20.sdp,
                top = windowInsets
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) {
        LoadingDialog(isLoading = viewModel.isLoading)

        Spacer(modifier = Modifier.weight(1f))

        Box {


            AsyncImage(
                model = if (viewModel.imageUri.value == null) R.drawable.baseline_account_circle_24 else viewModel.imageUri.value,
                contentDescription = "account pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.sdp)
                    //.border(2.dp, greenPrimary, CircleShape)
                    .clip(CircleShape)

            )


            Icon(
                Icons.Filled.Edit,
                contentDescription = "edit mark",
                tint = greenPrimary,
                modifier = Modifier
                    .padding(top = 24.sdp, end = 24.sdp)
                    .align(Alignment.TopEnd)
                    .clickable {

                        mediaPicker
                            .launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                    }
            )


        }

        Spacer(modifier = Modifier.height(35.sdp))

        LoginTextField(viewModel.username, label = "Name", error = viewModel.usernameError)

        Spacer(modifier = Modifier.height(35.sdp))

        LoginTextField(
            text = viewModel.mobileNumber,
            label = "Phone Number",
            error = viewModel.mobileNumberError,
            isPhoneNumber = true
        )

        Spacer(modifier = Modifier.height(35.sdp))

        Text(
            text = "Location",
            color = greyLabels,
            fontSize = 16.ssp,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded.value ,
            onExpandedChange ={

                expanded.value = it

            },
            modifier = Modifier.fillMaxWidth(1f)

        ) {

            /*if (viewModel.listOfAddresses.isEmpty()){

                TextField(
                    readOnly = true,
                    value = "Add New Address",
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .clickable {

                            openAddressBook()
                        },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
                )
            } else {

             */


                TextField(
                    readOnly = true,
                    value = selectLocationText.value?:"Select your address",
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded.value
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(1f),
                    colors = ExposedDropdownMenuDefaults.textFieldColors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
                )

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {
                        expanded.value = false
                    },

                    modifier = Modifier.fillMaxWidth(1f)
                        .background(Color.White)
                ) {

                    viewModel.listOfAddresses.forEach { addressItem ->

                        DropdownMenuItem(
                            text = { Text(text = addressItem.label?:"") },

                            onClick = {

                                selectLocationText.value = addressItem.label?:""

                                viewModel.readableAddress.value = addressItem.label?:""
                                expanded.value = false
                            })

                    }

                    DropdownMenuItem(
                        text = { Text(text = "Add new address") },

                        onClick = {

                                openAddressBook()
                        })

                }

        }


        if (viewModel.readableAddressError.value!=null){
            Text(
                text = viewModel.readableAddressError.value?:"",
                color = Color.Black,
                fontSize = 18.ssp,
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(35.sdp))
        
        
        WideButton(buttonText = "SUBMIT") {


            viewModel.addUserToDataBase()

            
        }

        Spacer(modifier = Modifier.weight(1f))

    }


}
