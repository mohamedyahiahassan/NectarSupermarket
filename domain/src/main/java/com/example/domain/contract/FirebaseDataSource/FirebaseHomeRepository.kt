package com.example.domain.contract.FirebaseDataSource

import com.example.domain.contract.Resource
import com.example.domain.model.OffersITem
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FirebaseHomeRepository {

    suspend fun getOffersList(sectionName:String): Flow<Resource<List<OffersITem?>>>
}