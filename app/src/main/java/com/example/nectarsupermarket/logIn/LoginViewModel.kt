package com.example.nectarsupermarket.logIn

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.example.domain.contract.repository.AuthenticationRepo
import com.example.nectarsupermarket.navigation.HomeApp
import com.example.nectarsupermarket.navigation.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepo: AuthenticationRepo,
):ViewModel() {

    val email = mutableStateOf<String>("")
    val emailError = mutableStateOf<String?>(null)

    val password = mutableStateOf<String>("")
    val passwordError = mutableStateOf<String?>(null)

    val isLoading = mutableStateOf<Boolean>(false)

    val loginComplete = mutableStateOf<Boolean>(false)

    val loginError = mutableStateOf<String>("")

    var path = mutableStateOf<Any?>(null)



    fun emailValidation() {

        if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {

            emailError.value = "Enter a valid email"
        } else {

            emailError.value = null
        }
    }

    fun passwordValidation() {


        if (password.value.length < 1) {

            passwordError.value = "Enter a valid password"

            return

        } else {

            passwordError.value = null
        }

    }

    fun loginValidation():Boolean{

        emailValidation()
        passwordValidation()

        if (emailError.value == null && passwordError.value == null) {

            return true
        } else {
            return false
        }

    }

    fun checkCurrentUser(){

        viewModelScope.launch(Dispatchers.Main) {

            authenticationRepo.checkCurrentUserStatus().collect{

                when(it){

                    is Resource.Error -> {

                        Log.e("error checking current user",it.message.toString())
                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {

                        if (it.data?.uid!=null){

                            getUserData()

                            isLoading.value = false

                            path.value = HomeApp


                            loginComplete.value = true

                        } else {

                            path.value = Login

                            loginComplete.value = false

                        }
                    }
                }
            }

        }
    }

    fun getUserData(){

        viewModelScope.launch(Dispatchers.IO) {

            authenticationRepo.getCurrentUserData().collect{

                when(it){
                    is Resource.Error ->  {

                        Log.e("error fetching user data",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true

                    }
                    is Resource.Success -> {

                        isLoading.value = false
                        loginComplete.value = true
                    }
                }
            }
        }
    }

    fun signIn(){

        if (loginValidation()==true) {

            viewModelScope.launch(Dispatchers.IO) {

                authenticationRepo.signIn(email.value, password.value).collect {

                    when (it) {

                        is Resource.Error -> {
                            Log.e("error", it.message.toString())
                            emailError.value = it.message
                            isLoading.value = false
                        }

                        is Resource.Loading -> {

                            isLoading.value = true
                        }
                        is Resource.Success -> {

                            getUserData()

                        }
                    }
                }


            }
        }
    }


}