package com.example.nectarsupermarket.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.HomeRepo
import com.example.domain.contract.repository.ShopRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val homeRepo: HomeRepo,
    private val shopRepo: ShopRepo

):ViewModel() {

    val searchQuery = mutableStateOf<String>("")

    val isSearchActive = mutableStateOf<Boolean>(false)

    val exclusiveOffersList = mutableStateListOf<Product>()

    val bestSellingList = mutableStateListOf<Product>()

    val isLoading = mutableStateOf(true)


    fun getOffers(sectionName:String){

        if (exclusiveOffersList.isEmpty() || bestSellingList.isEmpty() == true) {

            viewModelScope.launch(Dispatchers.IO) {

                homeRepo.getOffersList(sectionName).collect {

                    when (it) {
                        is Resource.Error -> {

                            Log.e("error getting offers", it.message.toString())

                            isLoading.value = false
                        }

                        is Resource.Loading -> {

                            isLoading.value = true
                        }

                        is Resource.Success -> {


                            if (it.data?.isNotEmpty() == true) {

                                it.data!!.forEachIndexed { index, offersItem ->

                                    if (offersItem != null) {

                                        Log.e("refrene", it.data.toString())

                                        getProductsInsideFavouriteList(
                                            offersItem.referenceInFireStore.toString(),
                                            it.data!!.size,
                                            index,
                                            sectionName
                                        )
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
}

    fun getProductsInsideFavouriteList(path:String,listSize:Int,index:Int,sectionName: String){

        viewModelScope.launch(Dispatchers.IO) {

            shopRepo.getProductUsingPath(path).collect{

                when(it){

                    is Resource.Error -> {

                        Log.e("error getting products in offers list",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true
                    }
                    is Resource.Success -> {

                        if (sectionName == "exclusiveOffers"){
                            it.data?.let { it1 -> exclusiveOffersList.add(it1) }

                        }else if (sectionName == "bestSelling"){
                            it.data?.let { it1 -> bestSellingList.add(it1) }

                        }
                    }

                }

                if (index == listSize -1){

                    isLoading.value = false
                }
            }


        }



    }

    fun addProductToCart(product: Product){

        viewModelScope.launch (Dispatchers.IO){

            shopRepo.addProductToCart(product.toCartItem(1,product.price,product.documentReferencePath)).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("Error Adding product external button",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {


                    }
                }
            }
        }
    }


    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }


}