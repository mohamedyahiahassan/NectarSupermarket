package com.example.nectarsupermarket.account.userLocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState

class MapViewModel:ViewModel() {

    val cairo = LatLng(30.033300033803428, 31.232999972999096)

    var cameraPositionState = CameraPositionState(position = CameraPosition.fromLatLngZoom(cairo,10f))

    var markerState = MarkerState(position = cameraPositionState.position.target)

    val currentUserLocation = mutableStateOf<LatLng?>(cairo)

    val selectedLocation =  mutableStateOf<LatLng?>(cairo)

    lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var locationCallback: LocationCallback

    val openAlertDialog = mutableStateOf<Boolean>(false)

    val isPermanentlyDenied = mutableStateOf<Boolean>(false)

    val openLoctionSettingsDialog = mutableStateOf<Boolean>(false)

    val addressAsString = mutableStateOf<String>("")

    // array that contains all required permissions
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)


    // check if permission was already granted
    fun isLocationPermissionAlreadyGranted(context: Context):Boolean {

        val locationPermissionsAlreadyGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return locationPermissionsAlreadyGranted
    }

    // check if location settings in on or off
    @SuppressLint("SuspiciousIndentation")
    fun checkSettingsQualified(context: Context) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .build()

        locationRequest.minUpdateIntervalMillis

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnCompleteListener {

            if (task.isSuccessful) {

                getLocation(context)

            } else {
                val exception = task.exception
                if (exception is ResolvableApiException) {

                    try {
                        openLoctionSettingsDialog.value = true

                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                }
            }
        }

    }

    //getting current location
    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {

                    openLoctionSettingsDialog.value = false

                    currentUserLocation.value = LatLng(location.latitude,location.longitude)

                    cameraPositionState.position = CameraPosition.fromLatLngZoom(currentUserLocation.value!!,10f)

                    selectedLocation.value = currentUserLocation.value

                    Log.e("location",currentUserLocation.toString())




                }
            }
        }

        locationCallback.let {

            val locationRequest: LocationRequest =
                LocationRequest.create().apply {
                    interval = 10000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }


            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    // get readable address of location selected
    /* fun getReadableLocation(context: Context){

         val geocoder = Geocoder(context)

         if (selectedLocation.value!=null){

             val geocoderAddress = geocoder.getFromLocation(selectedLocation.value?.latitude?:0.0,selectedLocation.value?.longitude?:0.0,1)

             readableAddress.value = geocoderAddress?.get(0)?.getAddressLine(0) ?: ""
         }

     }

     */

    fun transformLocationToString(){

        addressAsString.value = selectedLocation.value?.latitude.toString() + "," + selectedLocation.value?.longitude.toString()

    }

    // stops fetching location updates
    fun stopLocationUpdate(){

        try {

            val removeTask = fusedLocationClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("location", "Location Callback removed.")
                } else {
                    Log.d("location", "Failed to remove Location Callback.")
                }
            }
        } catch (se: SecurityException) {
            Log.e("location", "Failed to remove Location Callback.. $se")
        }
    }

    fun returnToDetails(context: Context){

        selectedLocation.value = markerState.position

        //getReadableLocation(context)

        transformLocationToString()

    }

    fun openLocationFromSettings(context: Context){

        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

        context.startActivity(intent)

        openLoctionSettingsDialog.value = false
    }

    fun goToLocationPermissionSettings(context: Context){

        openAlertDialog.value = false

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        val uri  = Uri.fromParts("package", context.packageName, null)

        intent.data = uri

        context.startActivity(intent)
    }


}