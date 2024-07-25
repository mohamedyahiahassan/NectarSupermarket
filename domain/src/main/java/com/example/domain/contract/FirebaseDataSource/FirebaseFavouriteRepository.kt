package com.example.domain.contract.FirebaseDataSource

import com.example.domain.contract.Resource
import com.example.domain.model.FavouriteItem
import kotlinx.coroutines.flow.Flow

interface FirebaseFavouriteRepository {

    suspend fun getFavouriteList(): Flow<Resource<List<FavouriteItem?>>>
}