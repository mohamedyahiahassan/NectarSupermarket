package com.example.data.repository

import com.example.domain.contract.FirebaseDataSource.FirebaseHomeRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.HomeRepo
import com.example.domain.model.OffersITem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(private val firebaseHomeRepository: FirebaseHomeRepository):HomeRepo {
    override suspend fun getOffersList(sectionName: String): Flow<Resource<List<OffersITem?>>> {

        return firebaseHomeRepository.getOffersList(sectionName)
    }

}