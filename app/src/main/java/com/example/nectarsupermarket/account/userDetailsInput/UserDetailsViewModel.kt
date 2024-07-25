package com.example.nectarsupermarket.account.userDetailsInput

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AddressItem
import com.example.domain.model.AppUser
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.example.domain.contract.repository.AccountRepo
import com.example.domain.contract.repository.AuthenticationRepo
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(

   private val authenticationRepo: AuthenticationRepo,
    private val accountRepo: AccountRepo

):ViewModel() {

    val username = mutableStateOf<String>("")
    val usernameError = mutableStateOf<String?>(null)

    val mobileNumber = mutableStateOf<String>("")
    val mobileNumberError = mutableStateOf<String?>(null)

    val addressAsStringLatLong = mutableStateOf<String>("")
    val readableAddress = mutableStateOf<String>("")
    val readableAddressError = mutableStateOf<String?>(null)
    var locationLatLng: LatLng? = null

    val imageUri = mutableStateOf<Uri?>(null)

    var imageDownloadLink:Uri? =null

    val isSubmittingUserDataComplete = mutableStateOf(false)

    val listOfAddresses = mutableStateListOf<AddressItem>()

    val isLoading = mutableStateOf(false)



    fun nameValidation() {
        if (username.value.isNullOrEmpty() ||username.value.first() == ' ' ||username.value.length < 4) {

            usernameError.value = "Invalid Name"

        } else {

            usernameError.value = null
        }
    }

    fun phoneValidation(){

        if (mobileNumber.value.isNullOrEmpty() ||
            mobileNumber.value[0] != '0' ||
            mobileNumber.value[1] != '1' ||
            mobileNumber.value.length != 11){

            mobileNumberError.value = "Enter a valid mobile number"
        } else {

            mobileNumberError.value = null
        }
    }



    fun addUserToDatabaseCheck():Boolean{

        nameValidation()
        phoneValidation()

            if ( usernameError.value == null && mobileNumberError.value == null && readableAddressError.value == null) {

                return true
            } else {
                return false
            }

        }


    fun uploadUserImage(){

        viewModelScope.launch (Dispatchers.IO){

            imageUri.value?.let {
                authenticationRepo.addImage(it).collect{

                    when(it){
                        is Resource.Error -> {

                            Log.e("error uploading image",it.message.toString())
                            isLoading.value = false
                        }

                        is Resource.Loading -> {
                            isLoading.value = true
                        }

                        is Resource.Success -> {

                            getImageDownloadLink()

                        }
                    }
                }
            }
        }

    }

    fun getImageDownloadLink(){

        viewModelScope.launch (Dispatchers.IO){

            Log.e("entered download user","entered download user")

           authenticationRepo.getDownloadLink().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error uploading image",it.message.toString())

                        isLoading.value =false
                    }

                    is Resource.Loading -> {

                        isLoading.value =true

                    }

                    is Resource.Success -> {

                        Log.e("inside Resourse sucess image link",it.data.toString())

                        Log.e("1",it.data?.path.toString())

                        imageDownloadLink = it.data

                        Log.e("image",imageDownloadLink.toString())

                        isLoading.value =false
                    }
                }
            }

        }
    }

    fun addUserToDataBase(){

        if (addUserToDatabaseCheck() == true){

            viewModelScope.launch (Dispatchers.IO){

                isLoading.value = true

                User.appUser = AppUser(

                    uid = User.appUser?.uid,
                    firstName = username.value,
                    email = User.user?.email,
                    userImage = imageDownloadLink.toString(),
                    phoneNumber = mobileNumber.value,
                    latitude = locationLatLng?.latitude.toString(),
                    longitude = locationLatLng?.latitude.toString(),
                    selectedAddress = readableAddress.value,
                    selectedAddressPath = "/users/${User.user?.uid}/addresses/${readableAddress.value}"
                )


                authenticationRepo.addNewUser(User.appUser!!).collect{

                    when(it){
                        is Resource.Error -> {

                            Log.e("error updating user's data ",it.message.toString())
                        }
                        is Resource.Loading -> {

                            isLoading.value = true
                        }
                        is Resource.Success -> {

                            isSubmittingUserDataComplete.value = true

                            isLoading.value = false
                        }
                    }
                }




            }


        }
    }

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

}


