package com.example.nectarsupermarket.explore

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CategoriesItem
import com.example.domain.model.Product
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.ShopRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepo: ShopRepo,
):ViewModel() {

    val listOfCategories = mutableStateListOf<CategoriesItem>()

    val selectedCategory = mutableStateOf<String>("")

    val listOfProducts = mutableStateListOf<Product>()

    val selectedProduct = mutableStateOf<Product?>(null)

    val isLoading = mutableStateOf(true)

    val selectedAmountOfProducts = mutableStateOf(1)

    val totalItemPrice = mutableStateOf(selectedProduct.value?.price)

    val searchQuery = mutableStateOf("")

    val searchActive = mutableStateOf(false)

    fun getProductCategories() {

        listOfProducts.clear()

        if (listOfCategories.isEmpty()) {

            viewModelScope.launch {

                shopRepo.getAllCategories().collect {

                    when (it) {

                        is Resource.Error -> {

                            Log.e("error fetching categories",it.message.toString())
                            isLoading.value = false
                        }
                        is Resource.Loading -> isLoading.value = true
                        is Resource.Success -> {

                            isLoading.value = false
                            it.data?.forEach {
                                if (it != null) {
                                    listOfCategories.add(it)
                                }
                            }
                        }
                    }
                }

            }
        }
    }


    fun getProductsInSelectedCategory() {

        if (listOfProducts.isEmpty()) {

            viewModelScope.launch {

                shopRepo.getAllProductsInCategory(selectedCategory.value).collect {

                    when (it) {

                        is Resource.Error -> {

                            Log.e("error from get products", it.message.toString())
                        }
                        is Resource.Loading -> {

                            isLoading.value = true
                        }
                        is Resource.Success -> {

                            isLoading.value = false

                            it.data?.forEach {

                                if (it != null) {
                                    listOfProducts.add(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    fun openSelectedProduct(){

        selectedAmountOfProducts.value = 1

        totalItemPrice.value = selectedProduct.value?.price
    }

    fun calculateTotalPrice(){

     totalItemPrice.value =  selectedAmountOfProducts.value * (selectedProduct.value?.price ?: 0.0)

    }

    fun increaseProductQuantity(){

        if (selectedAmountOfProducts.value >= selectedProduct.value?.quantity!!){

            return

        } else {

            selectedAmountOfProducts.value++
        }


    }

    fun decreaseProductQuantity(){

        if (selectedAmountOfProducts.value ==1) {

            return

        }else{

            selectedAmountOfProducts.value--
        }

    }

    fun addProductToCart(){

        viewModelScope.launch (Dispatchers.IO){

            if (selectedProduct.value!=null){

                shopRepo.addProductToCart(selectedProduct.value!!.toCartItem(
                    selectedAmountOfProducts.value,
                    totalItemPrice.value,
                    selectedProduct.value!!.documentReferencePath.toString())).collect{

                        when(it){
                            is Resource.Error -> {

                                Log.e("error adding product to cart",it.message.toString())
                            }
                            is Resource.Loading ->  {


                            }
                            is Resource.Success -> {


                            }
                        }
                }

            }


        }
    }

    fun deleteProductFromCart(){

        viewModelScope.launch (Dispatchers.IO){

            if (selectedProduct.value!=null){

                shopRepo.deleteProductFromCart(selectedProduct.value!!)

            }


        }
    }


    fun addProductToFavourite(){

        viewModelScope.launch (Dispatchers.IO){

            shopRepo.addProductToFavourite(selectedProduct.value!!).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error adding product to favourite",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {

                    }
                }
            }
        }

    }

    fun deleteProductFromFavourite(){

        viewModelScope.launch (Dispatchers.IO){

            shopRepo.deleteProductFromCart(selectedProduct.value!!).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("Error deleting favourite",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {


                    }
                }
            }

        }

    }

    fun getProductDetailsUsingPath(path:String, quantitySelected:Int?) {

        viewModelScope.launch(Dispatchers.IO) {

           shopRepo.getProductUsingPath(path).collect {

                when (it) {

                    is Resource.Error -> {

                        Log.e("error", it.message.toString())

                        isLoading.value = false
                    }

                    is Resource.Loading -> isLoading.value = true

                    is Resource.Success -> {

                        selectedProduct.value = it.data

                        if (quantitySelected != null) {
                            selectedAmountOfProducts.value = quantitySelected
                        }

                        calculateTotalPrice()

                        isLoading.value = false

                    }
                }


            }
        }


    }


}