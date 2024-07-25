package com.example.nectarsupermarket.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.contract.FirebaseDataSource.FirebaseAuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository):ViewModel() {


    fun logOut(){

        viewModelScope.launch {

            firebaseAuthenticationRepository.logout()
        }
    }

}