package com.example.nectarsupermarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nectarsupermarket.navigation.NectarNavigationGraph
import com.example.nectarsupermarket.logIn.LoginViewModel
import com.example.nectarsupermarket.ui.theme.NectarSupermarketTheme
import com.example.nectarsupermarket.utils.ShowSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel : LoginViewModel by viewModels()

    private val viewmodel : InternetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       installSplashScreen().apply {

            setKeepOnScreenCondition{

                loginViewModel.checkCurrentUser()

                loginViewModel.isLoading.value
            }


        }

        enableEdgeToEdge(

            // tells the system that we have a light background to status bar icons will always be dark
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT,android.graphics.Color.TRANSPARENT)
        )
        setContent {
            NectarSupermarketTheme {

                val networkStatus = viewmodel.status.collectAsState()

                Box (modifier = Modifier.fillMaxSize(1f)) {

                    loginViewModel.path.value?.let { NectarNavigationGraph(it) }
                    
                    LaunchedEffect(key1 = networkStatus.value) {

                        delay(2000)

                        if (networkStatus.value.name =="UNAVAILABLE" || networkStatus.value.name  == "LOST" || networkStatus.value.name  == "Loosing"){

                            viewmodel.visible.value = true
                        } else {

                            viewmodel.visible.value = false
                        }
                    }

                    ShowSnackBar(viewmodel.visible.value,Modifier.padding(bottom = 40.dp).align(Alignment.BottomCenter))

                }
            }
        }


    }


}
