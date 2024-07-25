package com.example.data.repository

import com.example.data.FirebaseDataSource.FirebaseFavouriteRepoImpl
import com.example.domain.contract.FirebaseDataSource.FirebaseFavouriteRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.FavouriteRepo
import com.example.domain.model.FavouriteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteRepoImpl @Inject constructor(private val firebaseFavouriteRepository: FirebaseFavouriteRepository): FavouriteRepo {

    override suspend fun getFavouriteList(): Flow<Resource<List<FavouriteItem?>>> {

        return firebaseFavouriteRepository.getFavouriteList()
    }


}