package com.example.nectarsupermarket.signUp

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AppUser
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.example.domain.contract.repository.AuthenticationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepo: AuthenticationRepo
):ViewModel() {

    val email = mutableStateOf<String>("")
    val emailError = mutableStateOf<String?>(null)

    val password = mutableStateOf<String>("")
    val passwordError = mutableStateOf<String?>(null)

    val isLoading = mutableStateOf<Boolean>(false)

    val signUpComplete = mutableStateOf<Boolean>(false)

    fun emailValidation() {

        if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {

            emailError.value = "Enter a valid email"
        } else {

            emailError.value = null
        }
    }

    fun passwordValidation() {

        val uppercase: Pattern = Pattern.compile("[A-Z]")
        val lowercase: Pattern = Pattern.compile("[a-z]")

        if (password.value.length < 3) {

            passwordError.value = "Password should be at least 8 characters"

            return

        } else {

            passwordError.value = null
        }

        if (!uppercase.matcher(password.value).find()) {

            passwordError.value = "Password should contain at least 1 Upper Case letter"
            return

        } else {

            passwordError.value = null
        }

        if (!lowercase.matcher(password.value).find()) {

            passwordError.value = "Password should contain at least 1 Lower Case letter"
            return

        } else {

            passwordError.value = null
        }

    }

    fun signUpValidation():Boolean{
        emailValidation()
        passwordValidation()

        if ( emailError.value == null && passwordError.value == null) {

            return true
        } else {
            return false
        }

    }



    fun signUp(){

        if (signUpValidation()) {

            viewModelScope.launch(Dispatchers.IO) {

               authenticationRepo.signUp(email.value, password.value).collect {

                    when (it) {

                        is Resource.Error -> {
                            Log.e("error signing up", it.message.toString())
                            emailError.value = it.message
                            isLoading.value = false
                        }

                        is Resource.Loading -> isLoading.value = true

                        is Resource.Success -> {

                            isLoading.value = false

                            signUpComplete.value = true
                        }
                    }


               }
                if (User.appUser != null) {

                    Log.e("app user add user viewmodel",User.appUser.toString())
                    authenticationRepo.addNewUser(

                        User.appUser!!
                    )


                }
            }
        }
    }
}