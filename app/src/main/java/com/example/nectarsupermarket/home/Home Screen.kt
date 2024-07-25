package com.example.nectarsupermarket.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Product
import com.example.nectarsupermarket.utils.LoadingDialog
import com.example.nectarsupermarket.explore.ProductItemOverview
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.utils.NectarSearchBar

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    onProductClick:(product: Product)->Unit,
    onSeeAllClick:(list:List<Product>, sectionTitle:String)->Unit){

    LaunchedEffect(key1 = Unit) {

        viewModel.getOffers("exclusiveOffers")
        viewModel.getOffers("bestSelling")
    }


   Column (
       modifier = Modifier
           .background(Color.White)
           .fillMaxSize(1f)
           .padding(start = 17.dp)
           .verticalScroll(rememberScrollState())
   ){


       Column (modifier = Modifier.padding(end = 17.dp)){

        //   Spacer(modifier = Modifier.height(17.dp))

           NectarSearchBar(
               searchQuery = viewModel.searchQuery,
               searchActive = viewModel.isSearchActive
           )

           Spacer(modifier = Modifier.height(17.dp))

           DiscountAds()

           Spacer(modifier = Modifier.height(17.dp))
       }

       OffersOverview(
           sectionTitle = "Exclusive Offers",
           list = viewModel.exclusiveOffersList,
           onProductClick = {product->

               onProductClick(product)
           },
           onAddClick =  {

               viewModel.addProductToCart(it)

           },
           onSeeAllClick = {sectionName->

               onSeeAllClick(viewModel.exclusiveOffersList,sectionName)
           })

       Spacer(modifier = Modifier.height(17.dp))

       OffersOverview(
           sectionTitle = "Best Selling",
           list = viewModel.bestSellingList,
           onProductClick = {product->

               onProductClick(product)
           },
           onAddClick =  {

               viewModel.addProductToCart(it)
           },
           onSeeAllClick = {sectionName ->

               onSeeAllClick(viewModel.bestSellingList,sectionName)
           })
       
   }

    LoadingDialog(isLoading = viewModel.isLoading)
}

@Composable
fun OffersOverview(
    sectionTitle:String,
    list: MutableList<Product>,
    onProductClick:(product: Product)->Unit,
    onAddClick:(product: Product)->Unit,
    onSeeAllClick:(sectionTitle:String)->Unit){
    
    Column (
        modifier = Modifier.height(300.dp)
           // .fillMaxHeight(0.4f)
    ){
        
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = sectionTitle,
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black,

            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "See all",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = greenPrimary,
                modifier = Modifier
                    .padding(end = 17.dp)
                    .clickable {

                    onSeeAllClick(sectionTitle)
                }

                )
        }

        Spacer(modifier = Modifier.height(17.dp))

        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ){


            items(list){

                ProductItemOverview(
                    product = it,
                    onProductClick = {

                        onProductClick(it)
                    },
                    onAddClick = {

                        onAddClick(it)
                    }) {

                }

            }
        }
        
    }
}