package com.example.domain.model

data class AppUser(
    var uid: String? = null,
    val firstName: String? = null,
    var email: String? = null,
    var userImage:String? = null,
    var phoneNumber:String? = null,
    var latitude:String? = null,
    var longitude:String? = null,
    var selectedAddress:String?=null,
    var selectedAddressPath:String? = null

)
