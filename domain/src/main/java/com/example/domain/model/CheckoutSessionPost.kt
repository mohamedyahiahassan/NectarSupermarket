package com.example.domain.model

data class CheckoutSessionPost(

    val client:String = "mobile",
    val mode:String = "payment",
    val currency:String = "usd",
    val amount:String)
