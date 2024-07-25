package com.example.data.repository

import com.example.data.FirebaseDataSource.FirebaseProductRepoImpl
import com.example.domain.contract.FirebaseDataSource.FirebaseProductRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.ProductRepo
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(private val firebaseProductRepository: FirebaseProductRepository):ProductRepo {

    override suspend fun updateProduct(path: String, product: Product): Flow<Resource<Any>> {

        return firebaseProductRepository.updateProduct(path,product)
    }


}