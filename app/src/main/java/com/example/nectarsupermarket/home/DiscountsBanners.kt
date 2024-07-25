package com.example.nectarsupermarket.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DiscountAds() {

    val state = rememberPagerState()

    val slideImage = remember { mutableStateOf<Int>(R.drawable.banner_1) }


    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxWidth(1f)
    ) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(1f),
            count = 3,
            state = state
        ) { page ->
            when (page) {

                0 -> {
                    slideImage.value = R.drawable.banner_1
                }

                1 -> {
                    slideImage.value = R.drawable.banner_2
                }

            }


            Image(
                painterResource(slideImage.value),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(1f),
                contentScale = ContentScale.FillWidth
            )
        }
        DotsIndicator(
            totalDots = 3,
            selectedIndex = state.currentPage,
            selectedColor = greenPrimary,
            unSelectedColor = Color.White,
        )
    }
}

    @Composable
    fun DotsIndicator(
        totalDots: Int,
        selectedIndex: Int,
        selectedColor: Color,
        unSelectedColor: Color,
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
            // .wrapContentWidth()
            //.wrapContentHeight()

        ) {

            items(totalDots) { index ->
                if (index == selectedIndex) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(selectedColor)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(unSelectedColor)
                    )
                }

                if (index != totalDots - 1) {
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                }
            }
        }
    }