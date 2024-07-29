package com.example.nectarsupermarket.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nectarsupermarket.Constants
import com.example.nectarsupermarket.navigation.AppScreens
import com.example.nectarsupermarket.navigation.HomeApp
import com.example.nectarsupermarket.navigation.PaymentSucceeded
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import kotlinx.serialization.ExperimentalSerializationApi


@OptIn(ExperimentalSerializationApi::class)
@Preview
@Composable
fun BottomNavigationBar(currentDestination: NavDestination?, onNavBarItemClick:(route:String)->Unit) {


    if (currentDestination?.route == AppScreens.HomeScreen.route ||
        currentDestination?.route == AppScreens.ExploreScreen.route ||
        currentDestination?.route == AppScreens.CartScreen.route ||
        currentDestination?.route == AppScreens.AccountScreen.route ||
        currentDestination?.route == AppScreens.FavouriteScreen.route
    ) {
        BottomAppBar(
            containerColor = Color.White
        ) {

            NavigationBar(
                containerColor = Color.White
            ) {

                Constants.listOfNavigationItems.forEach { navigationItem ->

                    val index = remember {
                        mutableStateOf(Constants.listOfNavigationItems.indexOf(navigationItem))
                    }



                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {

                            it.route == navigationItem.route ||
                                    it.route == navigationItem.extraScreenRoute1 ||
                                    it.route == navigationItem.extraScreenRoute2

                        } == true,

                        onClick = {

                            onNavBarItemClick(navigationItem.route.toString())



                        },
                        label = {
                            Text(
                                text = navigationItem.name.toString(),
                                fontFamily = gilroyFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.ssp,
                                color = if (currentDestination?.hierarchy?.any {

                                        it.route == navigationItem.route ||
                                                it.route == navigationItem.extraScreenRoute1 ||
                                                it.route == navigationItem.extraScreenRoute2
                                    } == true) greenPrimary else Color.Black,
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                        icon = {
                            if (currentDestination?.hierarchy?.any {

                                    it.route == navigationItem.route ||
                                            it.route == navigationItem.extraScreenRoute1 ||
                                            it.route == navigationItem.extraScreenRoute2

                                } == true) {

                                Image(
                                    painter = painterResource(
                                        id = navigationItem.selectedImage ?: 0
                                    ),
                                    contentDescription = "${navigationItem.name} selected icon ",
                                    modifier = Modifier.size(24.sdp)
                                )
                            } else {

                                Image(
                                    painterResource(id = navigationItem.unSelectedImage ?: 0),
                                    contentDescription = "${navigationItem.name} icon",
                                    modifier = Modifier.size(24.sdp)
                                )
                            }

                        })


                }
            }
        }


    } else {

        return
    }
}