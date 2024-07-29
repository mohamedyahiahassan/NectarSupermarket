package com.example.nectarsupermarket.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.LoadingDialog
import com.example.nectarsupermarket.utils.SwipeToDeleteContainer
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp


@Composable
fun FavoriteContent(viewModel: FavouriteViewModel, navigateToCart:()->Unit){

    LaunchedEffect(key1 = Unit) {

        viewModel.getFavouriteList()
    }

    LaunchedEffect(key1 = viewModel.isAddedToCart.value ) {

        if (viewModel.isAddedToCart.value == true){

            navigateToCart()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(start = 20.sdp, end = 20.sdp)
        ,
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.sdp)

        ) {


            if (viewModel.isLoading.value == true) {

                items(1) {

                    LoadingDialog(isLoading = viewModel.isLoading)
                }

            } else if (viewModel.isLoading.value == false && viewModel.favouriteList.isEmpty() == false) {


                itemsIndexed(viewModel.favouriteList,
                    key = { index, product ->
                        product.id!!
                    }
                ) { index, product ->

                    if (index == 0) {

                        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

                        Spacer(modifier = Modifier.height(20.sdp))
                    }


                    SwipeToDeleteContainer(
                        item = index,
                        onDelete = {

                            viewModel.deleteTaskFromFavourite(product)

                        }
                    ) {

                        FavouriteItem(product) {

                        }
                    }


                    Spacer(modifier = Modifier.height(20.sdp))

                    HorizontalDivider(color = greyBorder, thickness = 1.sdp)
                }

            } else if (viewModel.favouriteList.isEmpty() == true) {

                items(1) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(1f)
                    ) {


                        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

                        Spacer(modifier = Modifier.height(250.sdp))

                        Image(
                            painter = painterResource(id = R.drawable.baseline_favorite_24),
                            contentDescription = "add products to cart",
                            modifier = Modifier.size(100.sdp)
                        )

                        Spacer(modifier = Modifier.height(10.sdp))

                        Text(
                            text = "Your favourite list is empty",
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.ssp,
                            color = greyLabels,
                        )
                    }
                }
            }
        }

        //Spacer(modifier = Modifier.weight(1f))

        if (viewModel.favouriteList.isEmpty()){

            return
        } else {


            Button(
                onClick = {
                    viewModel.addAllFavouritesToCart()
                },
                shape = RoundedCornerShape(19.sdp),
                colors = ButtonDefaults.buttonColors(greenPrimary),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(65.sdp)
                    .align(Alignment.CenterHorizontally)
            ) {

                Text(
                    text = "Add All to Cart",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.ssp,
                    color = Color.White
                )

            }
        }

    }

}