package com.example.nectarsupermarket.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
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
import com.example.domain.model.Product
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels

@Composable
fun FavouriteItem(product: Product, openProductDetails:()->Unit){

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(1f)
    ){

        AsyncImage(
            model = product.image ,
            contentDescription ="favourite list item image",
            modifier = Modifier.fillMaxWidth(0.2f),
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Text(
                text = product.name?:"",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = product.unit?:"",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = greyLabels
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = product.price.toString()+ " LE",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black,
        )



        IconButton(
            onClick = {

                openProductDetails()
            }) {

            Image(
                painter = painterResource(id = R.drawable.arrow_forward_icon),
                contentDescription = "go to product from favourite" )
        }







    }

}