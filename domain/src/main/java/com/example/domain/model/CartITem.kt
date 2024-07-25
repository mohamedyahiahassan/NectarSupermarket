package com.example.domain.model

import com.google.firebase.firestore.DocumentId

data class CartITem(


    val referenceInFireStore: String? = null,
    val quantitySelected:Int? = null,
    val totalPrice:Double? = null,
    var product: Product? = null,


    @DocumentId
    val id:String?=null,

    ){


}
