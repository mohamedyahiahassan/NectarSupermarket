package com.example.domain.model

import com.google.firebase.Timestamp

data class Order(

    val orderName:String? = null,
    val orderStatus:Int? = null,
    val totalPrice:Double? = null,
    val created: Timestamp? = null,
    var latitude:String?=null,
    var longitude:String?=null,
    val addressPath:String?=null,
    val listOfOrderItems:HashMap<String,Any>?=null
)
