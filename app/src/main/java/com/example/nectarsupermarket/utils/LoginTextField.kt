package com.example.nectarsupermarket.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greyLabels
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    text:MutableState<String>,
    label:String,
    isEmail:Boolean = false,
    isPassword:Boolean = false,
    isPhoneNumber:Boolean = false,
    isLocation:Boolean = false,
    error:MutableState<String?>? = null,
    ){

    val keyboardOptions = remember {
        mutableStateOf<KeyboardOptions>(KeyboardOptions(keyboardType = KeyboardType.Text))
    }

    val passwordVisibility = remember {

        mutableStateOf<Boolean>(false)
    }

    val isReadOnly = remember {

        mutableStateOf<Boolean>(false)
    }

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = Unit) {

        if (isEmail == true) keyboardOptions.value = KeyboardOptions(keyboardType = KeyboardType.Email)
        if (isPassword == true) passwordVisibility.value = true
        if (isPhoneNumber== true) keyboardOptions.value = KeyboardOptions(keyboardType = KeyboardType.Number)
        if (isLocation == true) isReadOnly.value = true

    }

    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = label,
            color = greyLabels,
            fontSize = 16.ssp,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold
            )
        BasicTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.ssp, fontFamily = gilroyFont, fontWeight = FontWeight.Medium),
            modifier = Modifier
                .fillMaxWidth(1f),
            interactionSource = interactionSource,
            enabled = true,
            readOnly = isReadOnly.value,
            singleLine = true,
            keyboardOptions = keyboardOptions.value,
            visualTransformation = if (passwordVisibility.value == false)  VisualTransformation.None else PasswordVisualTransformation(),
        ){
            TextFieldDefaults.DecorationBox(
                value = text.value,
                innerTextField = it,
                interactionSource = interactionSource,
                enabled = true,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = greyLabels
            ),
            isError = error?.value!=null ,
            supportingText = {

                if(error?.value!=null){

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = error.value!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }


            },
            contentPadding = PaddingValues(0.sdp),
            visualTransformation = if (passwordVisibility.value == false)  VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isPassword == true) {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        if (passwordVisibility.value == true) {
                            Icon(
                                painter = painterResource(id = R.drawable.hide_password_icon),
                                contentDescription = "show password"
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.show_password_icon),
                                contentDescription = "hide password"
                            )
                        }


                    }
                }



            })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun testTextFiled(){

  //  LoginTextField(label = "Email", isEmail = false, isPassword = true)
}