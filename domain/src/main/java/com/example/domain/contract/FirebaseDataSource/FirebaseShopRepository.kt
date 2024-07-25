package com.example.domain.contract.FirebaseDataSource

import com.example.domain.model.CategoriesItem
import com.example.domain.model.Product
import com.example.domain.model.CartITem
import com.example.domain.contract.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseShopRepository {

    suspend fun getAllCategories(): Flow<Resource<List<CategoriesItem>>>

    suspend fun getAllProductsInCategory(categoryName:String):Flow<Resource<List<Product?>>>

    suspend fun getSpecificProduct(categoryName:String,productName:String):Flow<Resource<Product?>>

    suspend fun addProductToCart(cartITem: CartITem): Flow<Resource<Any>>

    suspend fun deleteProductFromCart(product: Product): Flow<Resource<Any>>

    suspend fun addProductToFavourite(product: Product):Flow<Resource<Any>>

    suspend fun deleteProductFromFavourite(product: Product):Flow<Resource<Any>>

    suspend fun getProductUsingPath(path:String): Flow<Resource<Product?>>
}