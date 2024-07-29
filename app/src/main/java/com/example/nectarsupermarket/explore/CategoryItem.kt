package com.example.nectarsupermarket.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.CategoriesItem
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.shimmerEffect
import com.example.nectarsupermarket.utils.ssp


@Composable
fun CategoryItem(categoriesItem: CategoriesItem?=null, color: Color? = null, isLoading: Boolean, navigateToProductList:(documentName:String)->Unit){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
           // .fillMaxWidth(0.45f)
            .aspectRatio(1f)
            .background(color?: greenPrimary, RoundedCornerShape(18.sdp))
            .clip(RoundedCornerShape(18.sdp))
            .shimmerEffect(isLoading)
            .clickable {

                navigateToProductList(categoriesItem?.documentName?:"")
            }

    ) {

        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = categoriesItem?.image,
            contentDescription = "category image",
            modifier = Modifier.fillMaxWidth(0.6f),
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = categoriesItem?.name.toString(),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 20.sdp, end = 20.sdp)
           // overflow = TextOverflow.Clip
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}

