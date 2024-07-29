package com.example.nectarsupermarket.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.contract.User
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.LoadingDialog
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.rememberPaymentSheet

@Composable
fun CartContent(
    viewModel: CartViewModel,
    navigateToProductDetails:(path:String,quantitySelected:Int)->Unit,
    navigateToPaymentSuccess:(orderNumber:String)->Unit
) {

    val context = LocalContext.current
    val paymentSheet = rememberPaymentSheet(viewModel::onPaymentSheetResult)

    LaunchedEffect(key1 = Unit) {

        PaymentConfiguration.init(context, User.publishableKey)

        if (viewModel.cartList.isEmpty()){
            viewModel.getCartList()
        }

    }

    LaunchedEffect(key1 = viewModel.isFetchingStripeDataComplete.value) {

        if (viewModel.isFetchingStripeDataComplete.value == true ){

            viewModel.customerConfig.value?.let {
                viewModel.paymentIntentClientSecret.value?.let { it1 ->
                    viewModel.presentPaymentSheet(paymentSheet, it,
                        it1
                    )
                }
            }

            viewModel.isFetchingStripeDataComplete.value = false
        }

    }

    LaunchedEffect(key1 = viewModel.isPaymentSuccessful.value) {


        if (viewModel.isPaymentSuccessful.value == true){

            navigateToPaymentSuccess(viewModel.orderPath.substringAfterLast("/"))
        }

        if (viewModel.isPaymentSuccessful.value == false){

            viewModel.openFailedPaymentDialog.value = true
        }

    }

    LoadingDialog(isLoading = viewModel.isLoadingGeneral)


    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(start = 20.sdp, end = 20.sdp)
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.sdp)

        ) {

            if (viewModel.isProductListLoading.value == true) {

                items(1) {

                    LoadingDialog(isLoading = viewModel.isProductListLoading)
                }
            } else if (viewModel.cartList.isEmpty() == false) {


                itemsIndexed(
                    viewModel.cartList, key = { index, cartItem ->
                        cartItem?.id!!
                        index
                    }
                ) { index, cartItem ->


                    if (index == 0) {

                         HorizontalDivider(color = greyBorder, thickness = 1.sdp)

                        Spacer(modifier = Modifier.height(20.sdp))
                    }

                    CartItem(cartItem,

                        decreaseProductInCart = {

                            viewModel.selectedProduct.value = cartItem

                            viewModel.decreaseProductQuantity(index)


                        },
                        increaseProductInCart = {

                            viewModel.selectedProduct.value = cartItem

                            viewModel.increaseProductQuantity(index)


                        },
                        deleteProductFromCart = {

                            viewModel.selectedProduct.value = cartItem

                            viewModel.deleteProductFromCart()

                        },
                        navigateToProductDetails = { path, selectedQuantity ->

                            navigateToProductDetails(path, selectedQuantity)
                        })

                    Spacer(modifier = Modifier.height(20.sdp))

                    HorizontalDivider(color = greyBorder, thickness = 1.sdp)

                }
            } else {

                items(1) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(1f)
                    ) {

                        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

                        Spacer(modifier = Modifier.height(250.sdp))

                        Image(
                            painter = painterResource(id = R.drawable.empty_cart),
                            contentDescription = "add products to cart"
                        )

                        Spacer(modifier = Modifier.height(10.sdp))

                        Text(
                            text = "Your cart is empty",
                            fontFamily = gilroyFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.ssp,
                            color = greyLabels,
                        )
                    }
                }
            }


        }

       // Spacer(modifier = Modifier.weight(1f))

        if (viewModel.cartTotalAmount.value==0.0){

            return
        } else {
            Button(
                onClick = {

                    viewModel.sendProductToStripeForPayment()
                },
                shape = RoundedCornerShape(19.sdp),
                colors = ButtonDefaults.buttonColors(greenPrimary),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(65.sdp)
                    .align(Alignment.CenterHorizontally)
            ) {

                Spacer(modifier = Modifier.width(20.sdp))
                Text(
                    text = "Go to Checkout",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.ssp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "LE " + viewModel.cartTotalAmount.value.toString(),
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.ssp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(20.sdp))

            }
        }
    }

    Box (modifier = Modifier
        .fillMaxSize(1f)
        .padding(20.sdp)){



        OrderFailedDialog(
            isDialogOpen = viewModel.openFailedPaymentDialog.value,
            onDismiss = {

            viewModel.openFailedPaymentDialog.value = false
        },
            tryPaymentAgain = {

                viewModel.sendProductToStripeForPayment()
            })
    }
}
