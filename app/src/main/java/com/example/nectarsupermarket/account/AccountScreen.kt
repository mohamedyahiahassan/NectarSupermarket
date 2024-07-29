package com.example.nectarsupermarket.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.AppUser
import com.example.domain.contract.User
import com.example.nectarsupermarket.AccountItem
import com.example.nectarsupermarket.Constants
import com.example.nectarsupermarket.R
import com.example.nectarsupermarket.ui.theme.gilroyFont
import com.example.nectarsupermarket.ui.theme.greenPrimary
import com.example.nectarsupermarket.ui.theme.greyLabels
import com.example.nectarsupermarket.ui.theme.greyLabels2
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectarsupermarket.ui.theme.greyBorder
import com.example.nectarsupermarket.utils.sdp
import com.example.nectarsupermarket.utils.ssp

@Composable
fun AccountContent(
    viewModel: AccountViewModel = viewModel(),
    navigateToRoot:()->Unit,
    openOrdersList:()->Unit,
    openUserDetails:()->Unit,
    openAddressBook:()->Unit) {

    Column (
        modifier = Modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.sdp)
    ){

        Spacer(modifier = Modifier.height(20.sdp))

        User.appUser?.let { AccountNameEmail(it) }

        Spacer(modifier = Modifier.height(10.sdp))

        HorizontalDivider(color = greyBorder, thickness = 1.sdp)


        AccountItem(item = Constants.listOfAccountSections[0]){

                    openOrdersList()
        }

        HorizontalDivider(color = greyBorder, thickness = 1.sdp)


        AccountItem(item = Constants.listOfAccountSections[1]){

            openUserDetails()
        }

        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

        AccountItem(item = Constants.listOfAccountSections[2]){

                openAddressBook()
        }

        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

        AccountItem(item = Constants.listOfAccountSections[6]){

        }

        HorizontalDivider(color = greyBorder, thickness = 1.sdp)

        AccountItem(item = Constants.listOfAccountSections[7]){

        }


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {

                viewModel.logOut()

                navigateToRoot()

            },
            shape = RoundedCornerShape(19.sdp),
            colors = ButtonDefaults.buttonColors(greyLabels2),
            modifier = Modifier
                .padding(start = 20.sdp, end = 20.sdp)
                .fillMaxWidth(1f)
                .height(65.sdp)
        ) {

            Text(
                text = "Log Out",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.ssp,
                color = greenPrimary
            )

        }

        Spacer(modifier = Modifier.height(20.sdp))


    }
}

@Composable
fun AccountNameEmail(user: AppUser) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 20.sdp, end = 20.sdp)
            .fillMaxWidth(1f)) {

        if(user.userImage == null){
            Image(
                painter = painterResource(id = R.drawable.account_icon),
                contentDescription = "account pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.sdp)
                    .clip(RoundedCornerShape(27.sdp))

            )
        } else {

            AsyncImage(
                model = user.userImage,
                contentDescription = "account pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.sdp)
                    .clip(RoundedCornerShape(27.sdp))

            )
        }


        Spacer(modifier = Modifier.width(20.sdp))

        Column {

            Text(
                text = User.appUser?.firstName?:"",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.ssp,
                color = Color.Black,
            )
            Text(
                text = User.appUser?.email?:"",
                fontFamily = gilroyFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.ssp,
                color = greyLabels,
            )
        }
    }
}

@Composable
fun AccountItem(item: AccountItem,onAccountItemClick:()->Unit){

    Row (
        modifier = Modifier
            .padding(start = 20.sdp, end = 20.sdp)
            .fillMaxWidth(1f)
            .clickable {
                onAccountItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ){

        Image(painter = painterResource(id = item.image?:0),
            contentDescription = "account item image",
            modifier = Modifier.size(24.sdp),
            )

        Spacer(modifier = Modifier.width(20.sdp))

        Text(
            text = item.name.toString(),
            fontFamily = gilroyFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.ssp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = {
                onAccountItemClick()

        }) {
            Image(
                painter = painterResource(id = R.drawable.arrow_forward_icon),
                contentDescription = "go to product from favourite",
                modifier = Modifier.size(18.sdp))
        }



    }
}