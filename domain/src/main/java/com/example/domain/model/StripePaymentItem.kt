package com.example.domain.model

import com.google.firebase.Timestamp

data class StripePaymentItem(

    val client:String = "mobile",
    val mode:String = "payment",
    val currency:String = "usd",
    val amount:String? = null,
    val created: Timestamp? = null,
    val customer:String?=null,
    val ephemeralKeySecret:String?=null,
    val paymentIntentClientSecret:String?=null,
    val setupIntentClientSecret:Any? = null


)
