package com.example.nectarsupermarket.utils


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    open :Boolean,
    isPermanentlyDenied:Boolean,
    isLocationTurnedOff:Boolean,
    onDismiss: () -> Unit,
    onConfirmation:() -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {



    val title = remember {

        mutableStateOf("Permission Required")
    }

    val message = remember {

        mutableStateOf("You need to grant location permission to add your address")

    }

    val buttonText = remember {

        mutableStateOf("Grant Permission")
    }

    if (isPermanentlyDenied == true){

        title.value = "Permission Required"

        message.value = "You need to grant location permission to add your address"

        buttonText.value = "Open Settings"

    } else if ( isLocationTurnedOff == true){

        title.value = "Access to location is disabled"

        message.value = "Nectar can't access your location"

        buttonText.value = "Turn on Location"

    } else{

        title.value = "Permission Required"

        message.value = "You need to grant location permission to add your address"

        buttonText.value = "Grant Permission"
    }

    if (open == true){

        AlertDialog(
            icon = {
                Icon(Icons.Filled.LocationOn, contentDescription = "location icon")
            },
            title = {
                Text(text = title.value)
            },
            text = {
                Text(text = message.value)
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        if (isPermanentlyDenied == true){
                            onGoToAppSettingsClick()
                        } else {
                            onConfirmation()
                        }

                    }
                ) {
                    Text(buttonText.value)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )

    }



        /*BasicAlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier)
        {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = "Permission required")

                Text(
                    text = "You need to grant location access to add your address")

                Divider()
                Text(
                    text = if(isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        }
    }

         */



}


/*
interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your camera so that your friends " +
                    "can see you in a call."
        }
    }
}

 */