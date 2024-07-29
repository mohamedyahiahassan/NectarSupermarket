package com.example.nectarsupermarket.cart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.CartITem
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.shimmerEffect
import com.example.nectarsupermarket.utils.ssp

@Composable
fun CartItem(
    cartITem: CartITem? = null,
    decreaseProductInCart:()->Unit,
    increaseProductInCart:()->Unit,
    deleteProductFromCart:()->Unit,
    navigateToProductDetails:(path:String,quantitySelected:Int)->Unit
    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = cartITem?.product?.image,
            contentDescription = "product image in cart",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(0.3f)
                .clickable {
                    cartITem?.referenceInFireStore?.let { cartITem.quantitySelected?.let { it1 ->
                        navigateToProductDetails(it,
                            it1
                        )
                    } }


                },

        )

        Spacer(modifier = Modifier.width(20.sdp))

        Column {

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.clickable {
                    cartITem?.referenceInFireStore?.let { cartITem.quantitySelected?.let { it1 ->
                        navigateToProductDetails(it,
                            it1
                        )
                    } }
                }
            ) {
                Column {

                    Text(
                        text = cartITem?.product?.name?:"",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.ssp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(7.sdp))

                    Text(
                        text = cartITem?.product?.unit?:"",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.ssp,
                        color = greyLabels,
                        modifier = Modifier.align(Alignment.Start)
                    )


                }

                Spacer(modifier = Modifier.weight(1f))


                    Image(
                        painter = painterResource(id = R.drawable.delete_cart_item_icon),
                        contentDescription = "add product icon",
                        modifier = Modifier
                            .clickable {
                                deleteProductFromCart()
                            }
                    )




            }

            Spacer(modifier = Modifier.height(10.sdp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {

                        decreaseProductInCart()
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                    modifier = Modifier
                        .background(Color.Transparent, RoundedCornerShape(17.sdp))
                        .border(1.sdp, greyLabels, RoundedCornerShape(17.sdp))

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.remove_product_cart_icon),
                        contentDescription = "add product icon",
                        modifier = Modifier.fillMaxSize(0.7f)
                    )

                }

                if (cartITem != null) {
                    Text(
                        text = cartITem.quantitySelected.toString(),
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.ssp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 20.sdp, end = 20.sdp)
                    )
                }

                IconButton(
                    onClick = {
                            increaseProductInCart()
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
                    modifier = Modifier
                        .background(Color.Transparent, RoundedCornerShape(17.sdp))
                        .border(1.sdp, greyLabels, RoundedCornerShape(17.sdp))

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.add_product_cart_icon),
                        contentDescription = "add product icon",
                        modifier = Modifier.fillMaxSize(0.7f)
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                if (cartITem != null) {
                    Text(
                        text = cartITem?.totalPrice.toString() + " LE",
                        fontFamily = gilroyFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.ssp,
                        color = Color.Black
                    )
                }
            }


        }
    }
}
