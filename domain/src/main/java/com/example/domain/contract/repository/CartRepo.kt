package com.example.domain.contract.repository

import com.example.domain.contract.Resource
import com.example.domain.model.CartITem
import com.example.domain.model.CheckoutSessionPost
import com.example.domain.model.Order
import com.example.domain.model.Product
import com.example.domain.model.StripePaymentItem
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface CartRepo {


    suspend fun getCartList(): Flow<Resource<List<CartITem?>>>

    suspend fun getProductsInCartList(cartITem: CartITem):Flow<Resource<Product?>>

    suspend fun postPaymentDetailsToFirebase(checkoutSessionPost: CheckoutSessionPost): Flow<Resource<String>>

    suspend fun getPaymentDetailsFromFirebaseStripe(path:String): Flow<Resource<StripePaymentItem?>>

    suspend fun addNewOrder(order: Order): Flow<Resource<Any>>


}