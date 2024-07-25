package com.example.domain.contract.repository

import com.example.domain.contract.Resource
import com.example.domain.model.OffersITem
import kotlinx.coroutines.flow.Flow

interface HomeRepo {

    suspend fun getOffersList(sectionName:String): Flow<Resource<List<OffersITem?>>>
}