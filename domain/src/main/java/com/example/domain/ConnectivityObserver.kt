package com.example.domain

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {


    fun observe(): Flow<Status>

    enum class Status{

        AVAILABLE, UNAVAILABLE, LOSING, LOST

    }
}