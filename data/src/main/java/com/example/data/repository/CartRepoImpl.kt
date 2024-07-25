package com.example.data.repository

import com.example.domain.contract.FirebaseDataSource.FirebaseCartRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.CartRepo
import com.example.domain.model.CartITem
import com.example.domain.model.CheckoutSessionPost
import com.example.domain.model.Order
import com.example.domain.model.Product
import com.example.domain.model.StripePaymentItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoImpl @Inject constructor(private val firebaseCartRepository: FirebaseCartRepository) : CartRepo {

    override suspend fun getCartList(): Flow<Resource<List<CartITem?>>> {

        return firebaseCartRepository.getCartList()
    }

    override suspend fun getProductsInCartList(cartITem: CartITem): Flow<Resource<Product?>> {

        return firebaseCartRepository.getProductsInCartList(cartITem)
    }

    override suspend fun postPaymentDetailsToFirebase(checkoutSessionPost: CheckoutSessionPost): Flow<Resource<String>> {

        return firebaseCartRepository.postPaymentDetailsToFirebase(checkoutSessionPost)
    }

    override suspend fun getPaymentDetailsFromFirebaseStripe(path: String): Flow<Resource<StripePaymentItem?>> {

        return firebaseCartRepository.getPaymentDetailsFromFirebaseStripe(path)
    }

    override suspend fun addNewOrder(order: Order): Flow<Resource<Any>> {

        return firebaseCartRepository.addNewOrder(order)
    }


}