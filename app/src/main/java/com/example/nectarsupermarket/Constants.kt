package com.example.nectarsupermarket

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.nectarsupermarket.navigation.AppScreens
import com.example.nectarsupermarket.ui.theme.color1
import com.example.nectarsupermarket.ui.theme.color2
import com.example.nectarsupermarket.ui.theme.color3
import com.example.nectarsupermarket.ui.theme.color4
import com.example.nectarsupermarket.ui.theme.color5
import com.example.nectarsupermarket.ui.theme.color6

object Constants {


    val listOfNavigationItems = listOf<NavigationItem>(

        NavigationItem("Shop",R.drawable.store_selected,R.drawable.store_unselected,
            AppScreens.HomeScreen.route,),
        NavigationItem("Explore",R.drawable.explore_selected,R.drawable.explore_unselected,
            AppScreens.ExploreScreen.route,AppScreens.ProductListScreen.route,AppScreens.ProductDetailsScreen.route),
        NavigationItem("Cart",R.drawable.cart_selected,R.drawable.cart_unselected,
            AppScreens.CartScreen.route
        ),
        NavigationItem("Favorite",R.drawable.favorites_selected,R.drawable.favorites_unselected,
            AppScreens.FavouriteScreen.route
        ),
        NavigationItem("Account",R.drawable.user_selected,R.drawable.user_unselected,
            AppScreens.AccountScreen.route
        ),
    )

    val listOfBackgroundColor = listOf(

        color1,color2, color3, color4,color5,color6,color1,color2, color3, color4,color5,color6
    )


  /*  val listOfCategoriesItem = listOf<CategoriesItem>(

        CategoriesItem("vegetables",Color.Red.toArgb(),R.drawable.vegatbles),
        CategoriesItem("oil",Color.Blue.toArgb(),R.drawable.oil),
        CategoriesItem("meat",Color.Green.toArgb(),R.drawable.meat),
        CategoriesItem("bread",Color.Yellow.toArgb(),R.drawable.bread),
        CategoriesItem("diary",Color.Cyan.toArgb(),R.drawable.diary),
        CategoriesItem("drinks",Color.Red.toArgb(),R.drawable.drinks),
        CategoriesItem("vegetables",Color.Red.toArgb(),R.drawable.vegatbles),
        CategoriesItem("oil",Color.Blue.toArgb(),R.drawable.oil),
        CategoriesItem("meat",Color.Green.toArgb(),R.drawable.meat),
        CategoriesItem("bread",Color.Yellow.toArgb(),R.drawable.bread),
        CategoriesItem("diary",Color.Cyan.toArgb(),R.drawable.diary),
        CategoriesItem("drinks",Color.Red.toArgb(),R.drawable.drinks)
    )

   */

    val listOfAccountSections = listOf<AccountItem>(

        AccountItem("Orders",R.drawable.orders_icon),
        AccountItem("My Details",R.drawable.account_details_icon),
        AccountItem("Delivery Address",R.drawable.delivery_address_icon),
        AccountItem("Payment Methods",R.drawable.payment_icon),
        AccountItem("Promo Code",R.drawable.promo_card_icon),
        AccountItem("Notifications",R.drawable.bell_icon),
        AccountItem("Help",R.drawable.help_icon),
        AccountItem("About",R.drawable.about_icon),

    )

    val listOfOrderStatus = listOf(

        OrderStatusItem("Order Placed","We have received your order",Modifier.alpha(0.5f),Color(0f,0f,0f,alpha = 0.5f)),
        OrderStatusItem("Order Confirmed","Your order has been confirmed",Modifier.alpha(0.5f),Color(0f,0f,0f,alpha = 0.5f)),
        OrderStatusItem("Order Shipped","We order is on its way",Modifier.alpha(0.5f),Color(0f,0f,0f,alpha = 0.5f)),
        OrderStatusItem("Order Delivered","Your order has successfully been delivered",Modifier.alpha(0.5f),Color(0f,0f,0f,alpha = 0.5f)),
    )

}


data class AccountItem(

    val name:String? = null,
    val image:Int? = null,
)

data class NavigationItem(
    val name:String? = null,
    val selectedImage:Int? = null,
    val unSelectedImage:Int? = null,
    val route:String? = null,
    val extraScreenRoute1:String? = "",
    val extraScreenRoute2: String? = ""

)

data class OrderStatusItem(

    val title:String,
    val desc:String,
    var modifier: Modifier,
    var dividerColor: Color
)