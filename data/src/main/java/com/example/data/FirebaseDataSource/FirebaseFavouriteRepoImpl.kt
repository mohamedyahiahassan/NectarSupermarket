package com.example.data.FirebaseDataSource

import com.example.data.FirebaseConstants
import com.example.domain.contract.User
import com.example.domain.contract.FirebaseDataSource.FirebaseFavouriteRepository
import com.example.domain.contract.Resource
import com.example.domain.model.FavouriteItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseFavouriteRepoImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore):FirebaseFavouriteRepository {

    override suspend fun getFavouriteList(): Flow<Resource<List<FavouriteItem?>>> = flow {


        val favouriteList = mutableListOf<FavouriteItem?>()
        emit(Resource.Loading())

        val result = try {

            val response = firebaseFirestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.FAVOURITE).get().await()


            response.documents.forEach {

                if (it!=null){
                    favouriteList.add(it.toObject(FavouriteItem::class.java))
                }

            }

            Resource.Success(favouriteList.toList())

        }catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)

    }
}