package com.example.nectarsupermarket.account.userLocation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.utils.PermissionDialog
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp
import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings


@Composable
fun MapsContent(viewModel: MapViewModel = viewModel(), navigateBack:(address:String)->Unit){

    val context = LocalContext.current

    viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    // responsible for showing system dialog to request permission (location in this case)
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->

            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                    viewModel.checkSettingsQualified(context)

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {

                if (viewModel.openAlertDialog.value == true){

                    viewModel.isPermanentlyDenied.value = true
                }else{

                    viewModel.openAlertDialog.value = true
                }

                }
            }

        })

    LaunchedEffect(key1 = Unit) {


        if(viewModel.isLocationPermissionAlreadyGranted(context) == true){

            viewModel.checkSettingsQualified(context)
        }else {

            locationPermissionLauncher.launch(viewModel.locationPermissions)
        }
    }

    LaunchedEffect(key1 = viewModel.cameraPositionState.isMoving) {

        viewModel.markerState.position = viewModel.cameraPositionState.position.target

        viewModel.selectedLocation.value = viewModel.markerState.position
    }

    DisposableEffect(key1 = viewModel.currentUserLocation.value ) {

        onDispose {
            viewModel.stopLocationUpdate()
        }
    }


    Box (
        modifier = Modifier.fillMaxSize()
    ) {


        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = viewModel.cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),


        ) {

        }
        
        IconButton(
            modifier = Modifier
                .padding(end = 20.sdp, bottom = 120.sdp)
                .align(Alignment.BottomEnd),
            onClick = {

                if(viewModel.isLocationPermissionAlreadyGranted(context) == true){

                    viewModel.checkSettingsQualified(context)
                }else {

                    locationPermissionLauncher.launch(viewModel.locationPermissions)
                }
            }) {

            Image(
                painter = painterResource(id = R.drawable.mylocation_icon),
                contentDescription = "go to your location",
                modifier = Modifier.size(64.sdp))

        }

        Image(
            painter = painterResource(id = R.drawable.baseline_location_pin_24),
            contentDescription = "location pin",
            modifier = Modifier.align(Alignment.Center))


       Button(
            onClick = {

                viewModel.returnToDetails(context)

               navigateBack(viewModel.addressAsString.value)

            },
            shape = RoundedCornerShape(19.sdp),
            colors = ButtonDefaults.buttonColors(greenPrimary),
            modifier = Modifier
                .padding(start = 20.sdp, end = 20.sdp, bottom = 25.sdp)
                .fillMaxWidth(1f)
                .height(65.sdp)
                .align(Alignment.BottomCenter)
        ) {

            Text(
                text = "Confirm Location",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.ssp,
                color = Color.White
            )

        }

        PermissionDialog(
            open = viewModel.openAlertDialog.value,
            isPermanentlyDenied = viewModel.isPermanentlyDenied.value,
            isLocationTurnedOff = false,
            onDismiss = {

                viewModel.openAlertDialog.value = false
            },
            onConfirmation = {

                locationPermissionLauncher.launch(viewModel.locationPermissions)

            },
            onGoToAppSettingsClick = {

                viewModel.goToLocationPermissionSettings(context)

            })

        PermissionDialog(
            open = viewModel.openAlertDialog.value,
            isPermanentlyDenied = viewModel.isPermanentlyDenied.value,
            isLocationTurnedOff = false,
            onDismiss = {
                viewModel.openAlertDialog.value = false
            },
            onConfirmation = {

                locationPermissionLauncher.launch(viewModel.locationPermissions)

            },
            onGoToAppSettingsClick = {

                viewModel.goToLocationPermissionSettings(context)

            })

        PermissionDialog(
            open = viewModel.openLoctionSettingsDialog.value,
            isPermanentlyDenied = false,
            isLocationTurnedOff = true,
            onDismiss = {

                viewModel.openLoctionSettingsDialog.value = false

            },
            onConfirmation = {

                viewModel.openLocationFromSettings(context)
            },
            onGoToAppSettingsClick = {
            })



    }
}