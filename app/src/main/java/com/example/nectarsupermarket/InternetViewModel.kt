package com.example.nectarsupermarket

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InternetViewModel @Inject constructor(

    connectivityObserver: ConnectivityObserver
):ViewModel() {


    val status:StateFlow<ConnectivityObserver.Status> = connectivityObserver.observe().stateIn(

        viewModelScope, SharingStarted.WhileSubscribed(5000),ConnectivityObserver.Status.UNAVAILABLE
    )

    val visible = mutableStateOf(false)


}