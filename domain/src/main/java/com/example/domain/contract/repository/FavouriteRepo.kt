package com.example.domain.contract.repository

import com.example.domain.contract.Resource
import com.example.domain.model.FavouriteItem
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FavouriteRepo {

    suspend fun getFavouriteList(): Flow<Resource<List<FavouriteItem?>>>
}