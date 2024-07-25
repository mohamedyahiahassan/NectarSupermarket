package com.example.nectarsupermarket.explore

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels

import androidx.lifecycle.viewmodel.compose.viewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailsContent(
    viewModel: ShopViewModel = viewModel(),
    productPath: String?=null,
    quantitySelected:Int? = null,
    ) {


LaunchedEffect(key1 = Unit) {

    if (productPath!=null){

        viewModel.getProductDetailsUsingPath(productPath,quantitySelected)
    }
}

val context = LocalContext.current

Column (modifier = Modifier.background(Color.White)){


    AsyncImage(
        model = viewModel.selectedProduct.value?.image,
        contentDescription = "product Image",
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(1f)

    )

    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(1f)
        ) {

            Column {
                Text(
                    text = viewModel.selectedProduct.value?.name?:"",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = viewModel.selectedProduct.value?.unit?:"",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = greyLabels,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val favoriteClicked = remember {
                mutableStateOf(false)
            }



            Image(
                painter = if (favoriteClicked.value == true) painterResource(id = R.drawable.favourite_filled_icon)
                else painterResource(id = R.drawable.favourite_icon),
                contentDescription = "favourite icon",
                modifier = Modifier.clickable {

                    if (favoriteClicked.value == false){


                            viewModel.addProductToFavourite()

                            viewModel.selectedProduct.value!!.favourite = true

                        favoriteClicked.value = true

                    } else{

                            viewModel.deleteProductFromFavourite()

                            viewModel.selectedProduct.value!!.favourite = false

                        favoriteClicked.value = false

                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {

                    viewModel.decreaseProductQuantity()
                    viewModel.calculateTotalPrice()
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(17.dp))
                    .border(1.dp, greyLabels, RoundedCornerShape(17.dp))

            ) {

                Image(
                    painter = painterResource(id = R.drawable.remove_product_cart_icon),
                    contentDescription = "add product icon",
                    modifier = Modifier.fillMaxSize(0.7f)
                )

            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = viewModel.selectedAmountOfProducts.value.toString(),
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(20.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            IconButton(
                onClick = {

                    viewModel.increaseProductQuantity()
                    viewModel.calculateTotalPrice()
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(17.dp))
                    .border(1.dp, greyLabels, RoundedCornerShape(17.dp))

            ) {

                Image(
                    painter = painterResource(id = R.drawable.add_product_cart_icon),
                    contentDescription = "add product icon",
                    modifier = Modifier.fillMaxSize(0.7f)
                )

            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${viewModel.totalItemPrice.value?:0}" + " LE",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        HorizontalDivider(color = greyLabels, thickness = 1.dp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Product Details",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = viewModel.selectedProduct.value?.desc?:"",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = greyLabels
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {

              viewModel.addProductToCart()

                Toast.makeText(context,"Product Added to Cart",Toast.LENGTH_SHORT).show()




            },
            shape = RoundedCornerShape(19.dp),
            colors = ButtonDefaults.buttonColors(greenPrimary),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(65.dp)
        ) {

            Text(
                text = "Add to Basket",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )

        }


        Spacer(modifier = Modifier.height(20.dp))

    }


}


}

