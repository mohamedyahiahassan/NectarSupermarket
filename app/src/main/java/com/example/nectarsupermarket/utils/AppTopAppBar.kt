package com.example.nectarsupermarket.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.navigation.AppScreens
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NectarTopAppBar(currentDestination: NavDestination?,
                    scrollBehavior: TopAppBarScrollBehavior,
                    categoryName:String,
                    onBackPressed:()->Unit){

   val title = remember {

       mutableStateOf("")
   }

    val backButtonShown = remember {
        mutableStateOf(false)
    }

    val isScrollableTopAppBar = remember {
        mutableStateOf(true)
    }

    val topAppBarColor = remember {

        mutableStateOf(Color.White)
    }


    LaunchedEffect(key1 = currentDestination?.route) {

        when(currentDestination?.route){

            AppScreens.HomeScreen.route ->{

                topAppBarColor.value = Color.White

                title.value = ""

                backButtonShown.value = false

                isScrollableTopAppBar.value = false
            }

            AppScreens.ExploreScreen.route -> {


                topAppBarColor.value = Color.White

                title.value = "Find Products"

                backButtonShown.value = false


                isScrollableTopAppBar.value = false
            }
            AppScreens.ProductListScreen.route -> {

                topAppBarColor.value = Color.White

                isScrollableTopAppBar.value = false

                title.value = categoryName

                backButtonShown.value = true

                isScrollableTopAppBar.value = true
            }
            AppScreens.ProductDetailsScreen.route-> {

                topAppBarColor.value = Color.White

                title.value = ""

                backButtonShown.value = true

                isScrollableTopAppBar.value = false

            }
            AppScreens.CartScreen.route-> {

                topAppBarColor.value = Color.White

                title.value = "My Cart"

                backButtonShown.value = false

                isScrollableTopAppBar.value = false

            }
            AppScreens.FavouriteScreen.route-> {

                topAppBarColor.value = Color.White

                title.value = "Favourite"

                backButtonShown.value = false

                isScrollableTopAppBar.value = false

            }
            AppScreens.AccountScreen.route-> {

                topAppBarColor.value = Color.White

                backButtonShown.value = false

                isScrollableTopAppBar.value = false

            }

            AppScreens.ProductsListFromHome.route->{

                topAppBarColor.value = Color.White

                title.value = categoryName

                isScrollableTopAppBar.value = false

                backButtonShown.value = true
            }

            AppScreens.OrdersScreen.route->{

                topAppBarColor.value = Color.White

                title.value = "Orders"

                backButtonShown.value = true

                isScrollableTopAppBar.value = false
            }

            else ->{

                topAppBarColor.value = Color.White

                title.value = ""

                backButtonShown.value = true

                isScrollableTopAppBar.value = false
            }
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(topAppBarColor.value),
        title = {
            Text(
                text = title.value,
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
            )
        },
        navigationIcon = {

            if (backButtonShown.value){

                IconButton(
                    onClick = {
                        onBackPressed()

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back_icon),
                        contentDescription = "back button"
                    )

                }

            }


        },

        scrollBehavior = if (isScrollableTopAppBar.value) scrollBehavior else TopAppBarDefaults.pinnedScrollBehavior()
    )

}

@Preview (showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(){

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
        title = {
            
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                
                Text(
                    text = "Nectar",
                    color = greenPrimary,
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,

                )

                Image(
                    painter = painterResource(id = R.drawable.logo_carrot_colored),
                    contentDescription = "nectar logo",
                    Modifier.size(36.dp))
            }
            
           /* Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "Delivering to",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Thin,
                    fontSize = 18.sp,
                    color = Color.Black,
                )

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = "Home",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = greenPrimary,
                    )

                    Image(
                        painter = painterResource(id = R.drawable.arrow_down_24),
                        contentDescription = "choose delivery location")


                }
            }
            
            */
        }
    )
}