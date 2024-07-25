package com.example.data.FirebaseDataSource

import com.example.domain.model.Product
import com.example.domain.contract.FirebaseDataSource.FirebaseProductRepository
import com.example.domain.contract.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseProductRepoImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore):FirebaseProductRepository {

    override suspend fun updateProduct(path:String, product: Product): Flow<Resource<Any>> = flow {

        firebaseFirestore.document(path).set(product)
    }
}