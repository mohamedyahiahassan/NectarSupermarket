package com.example.nectarsupermarket.cart

import android.app.Dialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.WideButton


@Composable
fun OrderFailedDialog(
    isDialogOpen:Boolean,
    onDismiss:()->Unit,
    tryPaymentAgain:()->Unit) {


    if (isDialogOpen == true) {

        Dialog(onDismissRequest = {

            onDismiss()
        }) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .aspectRatio(0.6f)
                    .background(Color.White, RoundedCornerShape(18.dp))
                    .clip(RoundedCornerShape(18.dp))
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                IconButton(
                    modifier = Modifier.align(Alignment.Start),
                    onClick = {

                        onDismiss()
                }) {

                    Image(painter = painterResource(id = R.drawable.delete_cart_item_icon),
                        contentDescription = "close dialog box",)

                }

                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.order_pack_image),
                    contentDescription = "order failed",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Oops! Order Failed",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Something went wrong.",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = greyLabels,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                WideButton(buttonText = "Try Again") {

                    tryPaymentAgain()
                }


                Spacer(modifier = Modifier.weight(0.1f))


            }

        }
    }
}