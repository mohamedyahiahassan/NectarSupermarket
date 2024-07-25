package com.example.nectarsupermarket.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.Constants
import com.example.nectarsupermarket.utils.NectarSearchBar


@Composable
fun ExploreContent(viewModel: ShopViewModel = viewModel(),
                   navigateToProductList:(categoryName:String)->Unit
) {

    LaunchedEffect(key1 = Unit) {

        viewModel.getProductCategories()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(1f)
            .padding(start = 20.dp, end = 20.dp)
            
    ) {

        NectarSearchBar(searchQuery = viewModel.searchQuery, searchActive = viewModel.searchActive)
        
        Spacer(modifier = Modifier.height(15.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {

            if (viewModel.isLoading.value == true) {

                items(10) {

                    CategoryItem(isLoading = viewModel.isLoading.value) {

                    }
                }
            } else {

                items(viewModel.listOfCategories.size) { item ->

                    CategoryItem(
                        categoriesItem = viewModel.listOfCategories[item],
                        color = Constants.listOfBackgroundColor[item],
                        isLoading = viewModel.isLoading.value,
                        navigateToProductList = {

                            viewModel.selectedCategory.value =
                                viewModel.listOfCategories[item].documentName.toString()

                            viewModel.getProductsInSelectedCategory()
                            

                            navigateToProductList(viewModel.selectedCategory.value)
                        }
                    )
                }


            }
        }
        
        Spacer(modifier = Modifier.height(15.dp))
    }
}