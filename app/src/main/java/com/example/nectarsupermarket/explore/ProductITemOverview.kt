package com.example.nectarsupermarket.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.Product
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.ui.theme.greyLabels

@Composable
fun ProductItemOverview(product: Product?, onProductClick:()->Unit, onAddClick:()->Unit, removeOnClick:()->Unit) {

    val productAdded = remember {

        mutableStateOf(false)
    }

    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .border(2.dp, greyBorder, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            //.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            .aspectRatio(0.7f),
        onClick = {
        onProductClick()
    }) {

        AsyncImage(
            model = product?.image,
            contentDescription = "product image",
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxHeight(0.55f)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillHeight
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = product?.name.toString(),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.Start)
        )

        // Spacer(modifier = Modifier.height(1.dp))

        Text(
            text = product?.unit.toString(),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = greyLabels,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.Start)
        )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            ) {
                Text(
                    text = product?.price.toString() + " LE",
                    fontFamily = gilroyFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))


                IconButton(
                    onClick = {

                        if (productAdded.value == false) {
                            onAddClick()

                            productAdded.value = true
                        } else {

                            removeOnClick()

                            productAdded.value = false
                        }


                    },
                    colors = IconButtonDefaults.iconButtonColors(greenPrimary),
                    modifier = Modifier.background(greenPrimary, RoundedCornerShape(17.dp))

                ) {

                    Image(

                        painter = if (productAdded.value == false) painterResource(id = R.drawable.add_icon)
                        else painterResource(id = R.drawable.product_added_icon),
                        contentDescription = "add product icon",
                        modifier = Modifier.fillMaxSize(0.85f)
                    )

                }
            }


    }
}
