package com.example.nectarsupermarket.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.domain.model.Product
import com.example.nectarsupermarket.account.AccountContent
import com.example.nectarsupermarket.account.AccountViewModel
import com.example.nectarsupermarket.cart.CartContent
import com.example.nectarsupermarket.explore.ExploreContent
import com.example.nectarsupermarket.favourite.FavoriteContent
import com.example.nectarsupermarket.home.HomeContent
import com.example.nectarsupermarket.cart.CartViewModel
import com.example.nectarsupermarket.cart.OrderAcceptedScreen
import com.example.nectarsupermarket.explore.ShopViewModel
import com.example.nectarsupermarket.explore.ProductDetailsContent
import com.example.nectarsupermarket.explore.ProductListContent
import com.example.nectarsupermarket.favourite.FavouriteViewModel
import com.example.nectarsupermarket.home.HomeViewModel
import com.example.nectarsupermarket.account.orders.OrderDetailsContent
import com.example.nectarsupermarket.account.orders.OrderTrackingContent
import com.example.nectarsupermarket.account.orders.OrderViewModel
import com.example.nectarsupermarket.account.orders.OrdersScreenContent
import com.example.nectarsupermarket.account.userLocation.InputAddressContent
import com.example.nectarsupermarket.account.userDetailsInput.UserDetailsContent
import com.example.nectarsupermarket.account.userDetailsInput.UserDetailsViewModel
import com.example.nectarsupermarket.account.userLocation.AddressViewModel
import com.example.nectarsupermarket.account.userLocation.AddressesBookContent
import com.example.nectarsupermarket.account.userLocation.MapViewModel
import com.example.nectarsupermarket.account.userLocation.MapsContent
import com.example.nectarsupermarket.utils.BottomNavigationBar
import com.example.nectarsupermarket.utils.HomeTopAppBar
import com.example.nectarsupermarket.utils.NectarTopAppBar
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppContent(returnToAppStartScreen:()->Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val categoryName = remember {

        mutableStateOf<String>("")
    }

    val tempList = remember {

        mutableStateListOf<Product>()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        topBar = {

            if (currentDestination?.route == AppScreens.HomeScreen.route){

                HomeTopAppBar()
            } else if ( currentDestination?.route == AppScreens.AccountScreen.route){

            } else {
                NectarTopAppBar(currentDestination,scrollBehavior,categoryName.value){
                    navController.navigateUp()

                }
            }

        },
        bottomBar = {
            BottomNavigationBar(currentDestination){route->

                navController.navigate(route = route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        //  saveState = true
                    }
                    launchSingleTop = true
                    // restoreState = true
                }
            }
        },
        modifier = Modifier
            //.padding(top = windowInsets.asPaddingValues().calculateTopPadding())
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(1f)) {

            NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {

                composable(route = AppScreens.HomeScreen.route) {
                    val viewModel = hiltViewModel<HomeViewModel>()

                    HomeContent(
                        viewModel = viewModel,
                        onProductClick = { product->


                            navController.navigate(ProductsDetailsFromElsewhere(path = product.documentReferencePath?:"",1))
                        },
                        onSeeAllClick = {list,sectionName->

                            categoryName.value = sectionName

                            tempList.clear()

                            tempList.addAll(list)

                            navController.navigate(AppScreens.ProductsListFromHome.route)

                        })
                }



                composable(route = AppScreens.ProductsListFromHome.route){
                    val viewModel = hiltViewModel<ShopViewModel>()

                   ProductListContent(viewModel,tempList,

                       navigateToProductDetails = {path->

                           val encodedUrl = URLEncoder.encode(path, StandardCharsets.UTF_8.toString())

                           navController.navigate(ProductsDetailsFromElsewhere(encodedUrl,1))


                   })
                }



                composable(route = AppScreens.ExploreScreen.route) {
                    val viewModel = hiltViewModel<ShopViewModel>()

                    ExploreContent(viewModel) {
                        navController.navigate(AppScreens.ProductListScreen.route)

                        categoryName.value = it
                    }
                }
                composable(route = AppScreens.ProductListScreen.route) {

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AppScreens.ExploreScreen.route)
                    }
                    val viewModel = hiltViewModel<ShopViewModel>(parentEntry)
                    ProductListContent(viewModel,
                        navigateToProductDetails = {

                        navController.navigate(AppScreens.ProductDetailsScreen.route)

                    })
                }


                composable(route = AppScreens.ProductDetailsScreen.route){

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AppScreens.ExploreScreen.route)
                    }
                    val viewModel = hiltViewModel<ShopViewModel>(parentEntry)

                    ProductDetailsContent(viewModel)
                }



                composable(route = AppScreens.CartScreen.route) {
                    val viewModel = hiltViewModel<CartViewModel>()
                    CartContent(

                        viewModel,
                        navigateToProductDetails = { path, selectedQuantity ->

                        val encodedUrl = URLEncoder.encode(path, StandardCharsets.UTF_8.toString())

                        navController.navigate(ProductsDetailsFromElsewhere(encodedUrl,selectedQuantity))
                    },
                        navigateToPaymentSuccess = {orderNumber->

                            navController.navigate(PaymentSucceeded(orderNumber)){

                                popUpTo(0)
                            }
                        })
                }

                composable<ProductsDetailsFromElsewhere> {
                    val viewModel = hiltViewModel<ShopViewModel>()

                    val productDFE : ProductsDetailsFromElsewhere = it.toRoute()

                  var path:String = ""

                  try {
                        val decoder : String? = URLDecoder.decode(productDFE.path,StandardCharsets.UTF_8.toString())

                      if (decoder != null) {
                          path = decoder
                      }
                    } catch (e: Exception) {

                    }

                    val selectedQuantity = productDFE.quantitySelected

                    ProductDetailsContent(viewModel,path,selectedQuantity)

                }


                composable<PaymentSucceeded> {

                    val paymentSucceeded : PaymentSucceeded = it.toRoute()

                    OrderAcceptedScreen(
                        navigateToHome = {

                            navController.navigate(AppScreens.HomeScreen.route){
                                popUpTo(0)
                            }
                        },
                        trackOrder = {

                            navController.navigate(OrderTrackingScreen(paymentSucceeded.orderNumber,1))
                        })
                }


                composable(AppScreens.FavouriteScreen.route) {
                    val viewModel = hiltViewModel<FavouriteViewModel>()


                    FavoriteContent(viewModel,
                       navigateToCart = {

                           navController.navigate(AppScreens.CartScreen.route)
                       })
                }



                composable(AppScreens.AccountScreen.route) {

                    val viewModel = hiltViewModel<AccountViewModel>()
                    AccountContent(
                        viewModel,
                        navigateToRoot = {

                       returnToAppStartScreen()
                    },
                        openOrdersList = {

                            navController.navigate(AppScreens.OrdersScreen.route)
                        },
                        openUserDetails = {

                            navController.navigate(UserDetailsScreen)
                        },
                        openAddressBook = {

                            navController.navigate(AddressBookScreen)
                        })
                }

                composable<UserDetailsScreen> {

                    val viewModel = hiltViewModel<UserDetailsViewModel>()


                    UserDetailsContent(
                        viewModel = viewModel,

                        openAddressBook = {

                            navController.navigate(AddressBookScreen)
                        },
                        navigateToApp = {

                            navController.navigateUp()

                        })

                }

                composable<AddressBookScreen> {

                    val viewModel = hiltViewModel<AddressViewModel>()

                    AddressesBookContent(
                        viewModel,
                        addNewOrEditAddress = {

                        navController.navigate(AddNewAddressScreen)
                    })

                }

                composable<AddNewAddressScreen> {

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AddressBookScreen)
                    }

                    val viewModel = hiltViewModel<AddressViewModel>(parentEntry)

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




                composable(AppScreens.OrdersScreen.route) {

                    val viewModel = hiltViewModel<OrderViewModel>()

                    OrdersScreenContent(
                        viewModel,
                        trackOrder = {orderNumber, orderStatus->

                            navController.navigate(OrderTrackingScreen(orderNumber,orderStatus))
                        },
                        navigateToOrderDetails = {

                            navController.navigate(AppScreens.OrderDetailsScreen.route)
                        })

                }

                composable(AppScreens.OrderDetailsScreen.route) {

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AppScreens.OrdersScreen.route)
                    }

                    val viewModel = hiltViewModel<OrderViewModel>(parentEntry)

                    OrderDetailsContent(viewModel) {orderNumber,orderStatus ->

                        navController.navigate(OrderTrackingScreen(orderNumber,orderStatus))
                    }

                }

                composable<OrderTrackingScreen> {

                    val viewModel = hiltViewModel<OrderViewModel>()

                    val orderTrackingScreen : OrderTrackingScreen= it.toRoute()

                    OrderTrackingContent(
                        viewModel = viewModel,
                        orderNumber = orderTrackingScreen.orderNumber,
                        orderStatus =  orderTrackingScreen.orderStatus)
                }









            }

        }
    }
}

// Makes sharedViewModel tied to the lifecycle of the navGraph selected
/*
@Composable

inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
*/
