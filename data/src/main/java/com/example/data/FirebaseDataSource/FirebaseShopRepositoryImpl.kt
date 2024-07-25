package com.example.data.FirebaseDataSource

import android.util.Log
import com.example.data.FirebaseConstants
import com.example.domain.model.CategoriesItem
import com.example.domain.model.FavouriteItem
import com.example.domain.model.Product
import com.example.domain.model.CartITem
import com.example.domain.contract.FirebaseDataSource.FirebaseShopRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseShopRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore):
    FirebaseShopRepository {

    override suspend fun getAllCategories(): Flow<Resource<List<CategoriesItem>>> = flow{

        emit(Resource.Loading())
        val list  = mutableListOf<CategoriesItem>()

        val result = try {

            val response = firestore.collection(FirebaseConstants.CATEGORIES).orderBy("order",Query.Direction.ASCENDING).get().await()


            response.documents.forEach {
                list.add(
                    CategoriesItem(it.data?.get("name").toString(),
                    it.data?.get("image").toString(),it.id
                )
                )
            }

            Resource.Success(list.toList())

        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)
    }

    override suspend fun getAllProductsInCategory(categoryName:String): Flow<Resource<List<Product?>>>  = flow{

        val list  = mutableListOf<Product?>(null)

        emit(Resource.Loading())
        val result = try {

            val response = firestore.collection(FirebaseConstants.CATEGORIES).document(categoryName).collection(FirebaseConstants.PRODUCTS).get().await().documents

            response.forEach {


                if (it!=null){

                    val product = it.toObject(Product::class.java)

                    product?.documentReferencePath = it.reference.path

                    list.add(product)
                }

            }

            Resource.Success(list.toList())

        } catch (e:Exception){

            Resource.Error(e.message.toString())

        }

        emit(result)


    }

    override suspend fun getSpecificProduct(categoryName:String,productName:String):Flow<Resource<Product?>> = flow {


        var product: Product?

        val result = try {

            val response = firestore.collection(FirebaseConstants.CATEGORIES).document(categoryName).collection(FirebaseConstants.PRODUCTS).document(productName).get().await()

            product = response.toObject(Product::class.java)

            Resource.Success(product)

        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)
    }

    override suspend fun addProductToCart(cartITem: CartITem): Flow<Resource<Any>> = flow {

        emit(Resource.Loading())
        val result = try{

            val response = firestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.CART).document(cartITem.id.toString()).set(cartITem)

            Resource.Success(response as Any)

        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)
    }

    override suspend fun deleteProductFromCart(product: Product): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        val result = try{

            val response = firestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.CART).document(product.id!!).delete()

            Resource.Success(response as Any)
        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)
    }

    override suspend fun addProductToFavourite(product: Product):Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val result = try{

            val response = firestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.FAVOURITE).document(product.id.toString()).set(
            FavouriteItem(product.documentReferencePath))

            Resource.Success(response as Any)
        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)

    }

    override suspend fun deleteProductFromFavourite(product: Product):Flow<Resource<Any>> = flow {

        emit(Resource.Loading())

        val result = try{

            val response = firestore.collection(FirebaseConstants.USERS).document(User.appUser?.uid.toString()).collection(FirebaseConstants.FAVOURITE).document(product.id?:"").delete()

            Resource.Success(response as Any)
        } catch (e:Exception){

            Resource.Error(e.message.toString())
        }
        emit(result)
    }

    override suspend fun getProductUsingPath(path:String): Flow<Resource<Product?>> = flow {

        var product: Product?

        val result = try {

            val response =   firestore.document(path).get().await()

            product = response.toObject(Product::class.java)

            product?.documentReferencePath = path

            Resource.Success(product)

        }catch (e:Exception){

            Resource.Error(e.message.toString())
        }

        emit(result)

    }


}