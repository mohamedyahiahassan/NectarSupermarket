package com.example.nectarsupermarket.account.userLocation

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AddressItem
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.AccountRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(
    private val accountRepo: AccountRepo
):ViewModel() {


    val listOfAddresses = mutableStateListOf<AddressItem>()

    val selectedAddress = mutableStateOf<AddressItem?>(null)

    val isLoading = mutableStateOf(true)

    val label = mutableStateOf<String?>(null)

    val city = mutableStateOf<String?>(null)

    val district = mutableStateOf<String?>(null)

    val street = mutableStateOf<String?>(null)

    val buildingNo = mutableStateOf<String?>(null)

    val floor = mutableStateOf<String?>(null)

    val aptNumber = mutableStateOf<String?>(null)

    val additionalAddressInfo = mutableStateOf<String?>(null)

    val readableAddress = mutableStateOf<String?>(null)

    val addressAsStringLatLong = mutableStateOf<String>("")

    val isAddingAddressSuccessFull = mutableStateOf(false)

    var addressLatitude = ""

    var addressLongitude = ""



    fun getAddresses(){

        viewModelScope.launch (Dispatchers.IO){

            accountRepo.getAddresses().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error fetching addresses",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {

                        it.data?.let { it1 -> listOfAddresses.addAll(it1) }

                        isLoading.value = false
                    }
                }
            }
        }
    }


    fun addNewAddress(){

        viewModelScope.launch (Dispatchers.IO){

            val address = AddressItem(label.value,city.value,district.value,street.value,buildingNo.value,floor.value,aptNumber.value,additionalAddressInfo.value,addressLatitude,addressLongitude)
            accountRepo.addNewAddress(address)
                .collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error adding address",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {

                        listOfAddresses.add(address)

                        isLoading.value = false

                        isAddingAddressSuccessFull.value = true
                    }
                }
            }
        }
    }

    fun getReadableLocationFromStringAddressLatLang(context: Context){

         addressLatitude = addressAsStringLatLong.value.substringBefore(',')

         addressLongitude = addressAsStringLatLong.value.substringAfter(',')

        val geocoder = Geocoder(context)

        val geocoderAddress = geocoder.getFromLocation(addressLatitude.toDouble(),addressLongitude.toDouble(),1)

        readableAddress.value = geocoderAddress?.get(0)?.getAddressLine(0) ?: ""


    }

    fun getReadableLocationSavedAddress(context: Context){

        val geocoder = Geocoder(context)

        val geocoderAddress = geocoder.getFromLocation(addressLatitude.toDouble(),addressLongitude.toDouble(),1)

        readableAddress.value = geocoderAddress?.get(0)?.getAddressLine(0) ?: ""


    }

    fun editAddress(){

        addressLatitude = selectedAddress.value?.latitude.toString()

        addressLongitude = selectedAddress.value?.longitude.toString()

        label.value = selectedAddress.value?.label

        city.value = selectedAddress.value?.city

        district.value = selectedAddress.value?.district

        street.value = selectedAddress.value?.street

        buildingNo.value = selectedAddress.value?.buildingNo

        floor.value = selectedAddress.value?.floorNo

        aptNumber.value = selectedAddress.value?.aptNo

        additionalAddressInfo.value =selectedAddress.value?.additionalInfo


    }



}