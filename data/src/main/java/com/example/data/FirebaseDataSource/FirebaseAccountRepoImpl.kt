package com.example.data.FirebaseDataSource

import com.example.data.FirebaseConstants
import com.example.domain.model.AddressItem
import com.example.domain.contract.User
import com.example.domain.contract.FirebaseDataSource.FirebaseAccountRepository
import com.example.domain.contract.Resource
import com.example.domain.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAccountRepoImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore,
    ):FirebaseAccountRepository {

    override suspend fun getOrders(): Flow<Resource<List<Order>>>  = flow {

        val listOfOrders= mutableListOf<Order>()

        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid!!).collection(FirebaseConstants.ORDERS).orderBy("created",Query.Direction.DESCENDING).get().await()

            response.documents.forEach {

                it.toObject(Order::class.java)?.let { it1 -> listOfOrders.add(it1) }
            }

            Resource.Success(listOfOrders.toList())

        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)
    }

    override suspend fun getAddresses():Flow<Resource<List<AddressItem>>> = flow {

        val listOfAddresses= mutableListOf<AddressItem>()

        emit(Resource.Loading())

        val result = try {

            val response =  firebaseFirestore
                .collection(FirebaseConstants.USERS)
                .document(User.appUser?.uid!!)
                .collection(FirebaseConstants.ADDRESSES).get().await()



            response.documents.forEach {

                it.toObject(AddressItem::class.java)?.let { it1 -> listOfAddresses.add(it1) }
            }

            Resource.Success(listOfAddresses.toList())

        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)
    }

    override suspend fun addNewAddress(address: AddressItem): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.ADDRESSES).document(address.label.toString()).set(address)


            Resource.Success(response as Any)

        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }
}