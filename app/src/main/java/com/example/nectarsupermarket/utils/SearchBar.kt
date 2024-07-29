package com.example.nectarsupermarket.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.ui.theme.greyLabels2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NectarSearchBar(searchQuery:MutableState<String>,searchActive:MutableState<Boolean>){


    OutlinedTextField(
        value = searchQuery.value ,
        onValueChange ={
            searchQuery.value = it
        },
        shape = RoundedCornerShape(25.sdp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(56.sdp)

        ,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
           // onSearch(searchQuery.value)
        }),
        leadingIcon = {

            Image(painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search_icon")

        },
        placeholder = {

            Text(
                text = "What do you search for?",
                fontSize = 14.ssp,
                color = Color.Black,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(

            focusedContainerColor= Color.Transparent,
            unfocusedContainerColor= Color.Transparent,
            disabledContainerColor= Color.Transparent,
            //focusedBorderColor= primaryBlue,
            //unfocusedBorderColor = primaryBlue,
            //disabledBorderColor = primaryBlue
        )

    )
}
