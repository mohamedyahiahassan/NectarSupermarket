package com.example.nectarsupermarket.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Product


@Composable
fun ProductListContent(
    viewModel: ShopViewModel = hiltViewModel(),
    list: MutableList<Product>? = null,
    navigateToProductDetails:(path:String)->Unit,


    ){


    LaunchedEffect(key1 = Unit) {

        if (list!=null){

            viewModel.listOfProducts.addAll(list)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp)

    ) {


        items(viewModel.listOfProducts.size){ itemIndex ->


           ProductItemOverview(viewModel.listOfProducts[itemIndex],
               onProductClick = {

                   viewModel.selectedProduct.value = viewModel.listOfProducts[itemIndex]

                   viewModel.openSelectedProduct()

                   viewModel.selectedProduct.value!!.documentReferencePath?.let {
                       navigateToProductDetails(
                           it
                       )
                   }
               },
               onAddClick = {

                   viewModel.selectedProduct.value = viewModel.listOfProducts[itemIndex]
                   viewModel.openSelectedProduct()
                   viewModel.addProductToCart()
               },
               removeOnClick = {
                   viewModel.selectedProduct.value = viewModel.listOfProducts[itemIndex]
                   viewModel.openSelectedProduct()
                   viewModel.addProductToCart()
                   viewModel.deleteProductFromCart()

               })
        }


    }


}

