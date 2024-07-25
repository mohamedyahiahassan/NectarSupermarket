package com.example.nectarsupermarket.logIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.windowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nectarsupermarket.ui.theme.gilroyFont
import androidx.compose.ui.unit.dp
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.utils.LoginTextField
import com.example.nectarsupermarket.utils.MultiStyleText
import com.example.nectarsupermarket.utils.WideButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInContent(viewModel: LoginViewModel = viewModel(), onLoginClicked:()->Unit, onSignUpClicked:()->Unit){

    LaunchedEffect(key1 = viewModel.loginComplete.value) {

        if (viewModel.loginComplete.value==true){
            onLoginClicked()
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .padding(top = windowInsets.asPaddingValues().calculateTopPadding(), start = 20.dp, end = 20.dp)


    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_carrot_colored) ,
            contentDescription = "colored_logo",
            modifier = Modifier.size(80.dp),
        //    contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Log In",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Enter your email and password",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = greyLabels,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(35.dp))

        LoginTextField(viewModel.email,label = "Email",isEmail =true, error = viewModel.emailError)

        Spacer(modifier = Modifier.height(35.dp))

        LoginTextField(viewModel.password,label = "Password", isPassword = true,error = viewModel.passwordError)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Forgot Password?",
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = greyLabels,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = viewModel.loginError.value,
            fontFamily = gilroyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(10.dp))

        WideButton(buttonText = "Log in",{

            viewModel.signIn()


        })

        Spacer(modifier = Modifier.height(20.dp))

        MultiStyleText(
            text1 = "Don't have an account? ",
            color1 = Color.Black,
            text2 ="Sign Up",
            color2 = greenPrimary,
            onClicked = {
                onSignUpClicked()
            })



    }
}