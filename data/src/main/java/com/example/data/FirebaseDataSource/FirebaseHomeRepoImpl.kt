package com.example.data.FirebaseDataSource

import android.util.Log
import com.example.domain.contract.FirebaseDataSource.FirebaseHomeRepository
import com.example.domain.contract.Resource
import com.example.domain.model.OffersITem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseHomeRepoImpl  @Inject constructor(private val firebaseFirestore: FirebaseFirestore):
    FirebaseHomeRepository {

    override suspend fun getOffersList(sectionName:String): Flow<Resource<List<OffersITem?>>> = flow {


        val offersList = mutableListOf<OffersITem?>()
        emit(Resource.Loading())

        val result = try {

            val response  =  firebaseFirestore.collection(sectionName).get().await()


            response.documents.forEach {

                offersList.add(it.toObject(OffersITem::class.java))

            }

            Resource.Success(offersList.toList())

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)


    }
}