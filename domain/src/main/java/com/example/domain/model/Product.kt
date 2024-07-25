package com.example.domain.model

import com.google.firebase.firestore.DocumentId


data class Product(

    val name:String? =null,
    val price:Double? = null,
    val quantity:Int? = null,
    val image:String? = null,
    val unit:String? =null,
    val desc:String? = null,
    var documentReferencePath:String?=null,
    var favourite: Boolean?=null,

    @DocumentId
    val id:String?=null
){

   fun toCartItem(quantitySelected:Int?,totalPrice:Double?,documentReferencePath: String?): CartITem {

       return CartITem(
           id = id,
           quantitySelected = quantitySelected,
           totalPrice = totalPrice,
           referenceInFireStore = documentReferencePath,
           )

    }
}
