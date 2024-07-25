package com.example.nectarsupermarket.favourite

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.FavouriteRepo
import com.example.domain.contract.repository.ShopRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val shopRepo: ShopRepo,
    private val favouriteRepo: FavouriteRepo
    ):ViewModel() {

    val favouriteList = mutableStateListOf<Product>()

    val isLoading = mutableStateOf(true)

    val isAddedToCart = mutableStateOf(false)


    fun getFavouriteList(){

        viewModelScope.launch (Dispatchers.IO){

           favouriteRepo.getFavouriteList().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("eror",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {


                        if (it.data?.isNotEmpty() == true){

                           it.data!!.forEachIndexed {index ,favouriteItem->

                               if (favouriteItem != null) {

                                   getProductsInsideFavouriteList(
                                       favouriteItem.path.toString(), it.data!!.size,index)
                               }
                           }




                        } else {

                            isLoading.value = false
                        }


                    }
                }
            }
        }
    }

    fun getProductsInsideFavouriteList(path:String,listSize:Int,index:Int){

        viewModelScope.launch(Dispatchers.IO) {

            shopRepo.getProductUsingPath(path).collect{

                when(it){

                    is Resource.Error -> {

                        Log.e("error",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {

                        it.data?.let { it1 -> favouriteList.add(it1) }

                    }
                }


            }

            if (index == listSize -1){

                isLoading.value = false
            }

        }
    }

    fun deleteTaskFromFavourite(product: Product){

        favouriteList.remove(product)

        viewModelScope.launch (Dispatchers.IO){

           shopRepo.deleteProductFromFavourite(product).collect {

               when (it) {
                   is Resource.Error -> {

                       Log.e("Error deleting task from favourite", it.message.toString())
                   }

                   is Resource.Loading -> {

                   }

                   is Resource.Success -> {


                   }
               }
           }
        }
    }

    fun addAllFavouritesToCart(){

        viewModelScope.launch (Dispatchers.IO){

            favouriteList.forEachIndexed {index,product->


                shopRepo.addProductToCart(product.toCartItem(1,product.price,product.documentReferencePath)).collect {

                    when (it) {
                        is Resource.Error -> {

                            isLoading.value = false

                            Log.e("Error adding all products to cart", it.message.toString())
                        }

                        is Resource.Loading -> {

                            isLoading.value = true
                        }

                        is Resource.Success -> {

                        }
                    }
                }
            }

           emptyFavouriteList()

        }

    }

    fun emptyFavouriteList() {

        favouriteList.forEachIndexed { index, product ->

            viewModelScope.launch(Dispatchers.IO) {

                shopRepo.deleteProductFromFavourite(product).collect {

                    when (it) {
                        is Resource.Error -> {

                            Log.e("Error deleting task from favourite", it.message.toString())

                            isLoading.value = false
                        }

                        is Resource.Loading -> {

                            isLoading.value = true

                        }

                        is Resource.Success -> {

                        }
                    }
                }
            }

            isAddedToCart.value = true
        }
    }
}