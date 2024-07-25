package com.example.data.networkConnectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.example.domain.ConnectivityObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityObserverImpl @Inject constructor( @ApplicationContext private val context: Context):ConnectivityObserver {


     val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {


        // convert callbackToFlow
        return callbackFlow {


            val callback = object : ConnectivityManager.NetworkCallback() {


                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    // launches coroutine to send flow
                    launch { send(ConnectivityObserver.Status.AVAILABLE) }

                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.LOSING) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.LOST) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.UNAVAILABLE) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            // suspends the callback flow until the coroutine scope launched in is canceled
            // (if called in viewmodel it will close the callback flow when the viewmodel lifecycle ends)
            awaitClose {

                connectivityManager.unregisterNetworkCallback(callback)
            }

            // it will make sure if there is two emissions of the same type that come one after the other
            // then it won't trigger. it will trigger only if there is a change in status
        }.distinctUntilChanged()
    }
}