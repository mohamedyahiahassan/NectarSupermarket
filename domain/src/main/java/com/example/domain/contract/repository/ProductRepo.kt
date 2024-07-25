package com.example.domain.contract.repository

import com.example.domain.contract.Resource
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {


    suspend fun updateProduct(path:String,product: Product):Flow<Resource<Any>>
}