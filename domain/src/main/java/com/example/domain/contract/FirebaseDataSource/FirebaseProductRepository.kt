package com.example.domain.contract.FirebaseDataSource

import com.example.domain.contract.Resource
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FirebaseProductRepository {

    suspend fun updateProduct(path:String,product: Product): Flow<Resource<Any>>
}