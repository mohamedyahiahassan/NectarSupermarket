package com.example.nectarsupermarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.domain.contract.User
import com.example.nectarsupermarket.account.userLocation.AddressViewModel
import com.example.nectarsupermarket.account.userLocation.MapsContent
import com.example.nectarsupermarket.logIn.LogInContent
import com.example.nectarsupermarket.logIn.LoginViewModel
import com.example.nectarsupermarket.signUp.OnBoarding
import com.example.nectarsupermarket.signUp.SignUpContent
import com.example.nectarsupermarket.signUp.SignUpViewModel
import com.example.nectarsupermarket.account.userDetailsInput.UserDetailsContent
import com.example.nectarsupermarket.account.userDetailsInput.UserDetailsViewModel
import com.example.nectarsupermarket.account.userLocation.AddressesBookContent
import com.example.nectarsupermarket.account.userLocation.InputAddressContent
import com.example.nectarsupermarket.account.userLocation.MapViewModel
import kotlinx.serialization.Serializable


// Route for Nested Graph
@Serializable
object Login

@Serializable
object HomeApp

// Routes inside Login nested graph
@Serializable
object OnBoardingScreen

@Serializable
object LoginScreen

@Serializable
object SignUpScreen

@Serializable
object UserDetailsScreen

@Serializable
object AddNewAddressScreen

@Serializable
object AddressBookScreen

@Serializable
object MapsScreen



// Routes inside HomeApp nested graph

@Serializable
object GoToAppScreens

sealed class AppScreens(val route:String){

    object HomeScreen: AppScreens("home_screen")

    object ProductsListFromHome:AppScreens("product_list_from_home")

    object ExploreScreen: AppScreens("explore_screen")

    object ProductListScreen : AppScreens("products_screen")

    object ProductDetailsScreen : AppScreens("product_details")

    object CartScreen: AppScreens("cart_screen")

  //  object PaymentSucceeded: AppScreens ("payment_succeeded")

    object FavouriteScreen: AppScreens("favorite_screen")

    object AccountScreen: AppScreens("account_screen")

    object OrdersScreen:AppScreens("orders_screen")

    object OrderDetailsScreen:AppScreens("order_details")
}

@Serializable
data class PaymentSucceeded(val orderNumber:String)

@Serializable
data class OrderTrackingScreen(val orderNumber:String, val orderStatus:Int)


@Serializable
data class ProductsDetailsFromElsewhere(val path :String, val quantitySelected:Int)

/*
@Serializable
data class ProductsListFromHome(val list: MutableList<Product>)

 */





@Composable
fun NectarNavigationGraph(startDestination:Any){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        ){

        navigation<Login>(startDestination = OnBoardingScreen){

            composable<OnBoardingScreen> {

                OnBoarding(){
                    navController.navigate(LoginScreen){
                        navController.popBackStack()
                    }

                }
            }

            composable<LoginScreen> {

                val viewModel = hiltViewModel<LoginViewModel>()
                LogInContent(viewModel,
                onLoginClicked ={
                    navController.navigate(GoToAppScreens){
                        popUpTo(0)
                    }
                } ,
                onSignUpClicked = {
                    navController.navigate(SignUpScreen)

                })
            }

            composable<SignUpScreen> {

               val viewModel = hiltViewModel<SignUpViewModel>()
                SignUpContent(viewModel,
                    onSignInClicked = {
                    navController.navigate(LoginScreen){
                        navController.popBackStack()
                    }
                },
                    navigateToUserDetails = {
                        navController.navigate(UserDetailsScreen){
                            popUpTo(0)
                        }
                    })
            }

            composable<UserDetailsScreen> {

                val viewModel = hiltViewModel<UserDetailsViewModel>()


                UserDetailsContent(
                    viewModel = viewModel,

                    openAddressBook = {

                        navController.navigate(AddNewAddressScreen)
                    },
                    navigateToApp = {

                        navController.navigate(HomeApp){
                            popUpTo(0)
                        }

                    })

            }

            composable<AddNewAddressScreen> {


                val viewModel = hiltViewModel<AddressViewModel>()

                var address:String? = null

                if (navController.currentBackStackEntry!!.savedStateHandle.contains("key")){

                    address = navController.currentBackStackEntry!!.savedStateHandle.get<String>("key")
                }

                InputAddressContent(
                    viewModel,
                    address,
                    returnToAddressBook = {

                        navController.navigateUp()

                    },
                    openMaps = {

                        navController.navigate(MapsScreen)
                    })

            }

            composable<MapsScreen> {


                val viewModel = viewModel<MapViewModel>()

                MapsContent(viewModel) {address->

                    navController.previousBackStackEntry?.savedStateHandle?.set("key",address)

                    navController.popBackStack()
                }
            }
        }

        navigation<HomeApp>(startDestination = GoToAppScreens){


            composable<GoToAppScreens>{
                AppContent(){

                    navController.navigate(Login){

                        popUpTo(0)

                        User.appUser = null
                    }
                }
            }


        }

    }



}



