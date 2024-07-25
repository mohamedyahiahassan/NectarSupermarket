package com.example.data.FirebaseDataSource

import android.util.Log
import com.example.data.FirebaseConstants
import com.example.domain.model.CheckoutSessionPost
import com.example.domain.model.Order
import com.example.domain.model.CartITem
import com.example.domain.contract.User
import com.example.domain.contract.FirebaseDataSource.FirebaseCartRepository
import com.example.domain.contract.Resource
import com.example.domain.model.Product
import com.example.domain.model.StripePaymentItem
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCartRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
): FirebaseCartRepository {


    override suspend fun getCartList(): Flow<Resource<List<CartITem?>>> = flow {

        val cartList = mutableListOf<CartITem?>()
        emit(Resource.Loading())

        val result = try {

            val response  = firebaseFirestore
                .collection(FirebaseConstants.USERS)
                .document(User.appUser?.uid.toString())
                .collection(FirebaseConstants.CART).get().await()


            response.documents.forEach {

                cartList.add(it.toObject(CartITem::class.java))
            }

            Resource.Success(cartList.toList())

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }

    override suspend fun getProductsInCartList(cartITem: CartITem):Flow<Resource<Product?>>  = flow{


        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore.document(cartITem.referenceInFireStore.toString()).get().await()

            Resource.Success((response.toObject(Product::class.java)))

        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)


    }

    override suspend fun postPaymentDetailsToFirebase(checkoutSessionPost: CheckoutSessionPost): Flow<Resource<String>> = flow {

        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore
                .collection(FirebaseConstants.USERS)
                .document(User.appUser?.uid.toString())
                .collection(FirebaseConstants.CHECKOUT_SESSION).add(checkoutSessionPost).await().path



            Resource.Success(response)
        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }

    override suspend fun getPaymentDetailsFromFirebaseStripe(path:String): Flow<Resource<StripePaymentItem?>> = flow {

        emit(Resource.Loading())

        val result = try {

            val response =  firebaseFirestore.document(path).get().await()

            val stripeItem = response.toObject(StripePaymentItem::class.java)

            Resource.Success(stripeItem)
        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }

    override suspend fun addNewOrder(order: Order): Flow<Resource<Any>>  = flow{

        emit(Resource.Loading())

        val result = try {

            val response =  firebaseFirestore
                .collection(FirebaseConstants.USERS)
                .document(User.appUser?.uid.toString())
                .collection(FirebaseConstants.ORDERS)
                .document(order.orderName?:"").set(order)

            Resource.Success(response as Any)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)


    }


}