package com.example.data.repository

import com.example.domain.contract.FirebaseDataSource.FirebaseShopRepository
import com.example.domain.model.CategoriesItem
import com.example.domain.model.Product
import com.example.domain.model.CartITem
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.ShopRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShopRepoImpl @Inject constructor(private val firebaseShopRepository: FirebaseShopRepository):ShopRepo {

    override suspend fun getAllCategories(): Flow<Resource<List<CategoriesItem>>> {

        return firebaseShopRepository.getAllCategories()
    }

    override suspend fun getAllProductsInCategory(categoryName: String): Flow<Resource<List<Product?>>> {

        return firebaseShopRepository.getAllProductsInCategory(categoryName)
    }


    override suspend fun getSpecificProduct(categoryName:String,productName:String): Flow<Resource<Product?>> {

        return firebaseShopRepository.getSpecificProduct(categoryName, productName)
    }

    override suspend fun addProductToCart(cartITem: CartITem): Flow<Resource<Any>> {

        return firebaseShopRepository.addProductToCart(cartITem)
    }

    override suspend fun deleteProductFromCart(product: Product): Flow<Resource<Any>>{

        return firebaseShopRepository.deleteProductFromCart(product)
    }

    override suspend fun addProductToFavourite(product: Product):Flow<Resource<Any>> {

        return firebaseShopRepository.addProductToFavourite(product)
    }

    override suspend fun deleteProductFromFavourite(product: Product):Flow<Resource<Any>> {

        return firebaseShopRepository.deleteProductFromFavourite(product)
    }

    override suspend fun getProductUsingPath(path:String): Flow<Resource<Product?>> {

        return firebaseShopRepository.getProductUsingPath(path)
    }
}