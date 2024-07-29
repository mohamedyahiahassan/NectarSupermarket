package com.example.nectarsupermarket.account.orders

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Order
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.example.domain.contract.repository.AccountRepo
import com.example.nectarsupermarket.Constants
import com.example.nectarsupermarket.OrderStatusItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val accountRepo: AccountRepo

):ViewModel() {

    var orderNumber:String = ""

    var orderStatus = 1

    var listOfOrderStatusItems = mutableStateListOf<OrderStatusItem>()

    val isLoading = mutableStateOf(true)

    val listOfOrders = mutableStateListOf<Order>()

    val selectedOrder = mutableStateOf<Order?>(null)

    var readableAddress = ""

     var listOfProductsInSelectedOrder : MutableCollection<ArrayList<String>>? =null


    fun updateVisualOrderStatus(){

        listOfOrderStatusItems = Constants.listOfOrderStatus.map {
            it.copy()
        }.toMutableStateList()


        if (orderStatus == 1) {

            listOfOrderStatusItems[0].modifier = Modifier.alpha(1f)


        } else if (orderStatus == 2) {


            listOfOrderStatusItems[0].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[0].dividerColor = Color.Green
            listOfOrderStatusItems[1].modifier = Modifier.alpha(1f)

        } else if (orderStatus== 3) {

            listOfOrderStatusItems[0].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[0].dividerColor = Color.Green
            listOfOrderStatusItems[1].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[1].dividerColor = Color.Green
            listOfOrderStatusItems[2].modifier = Modifier.alpha(1f)

        } else if (orderStatus == 4) {

            listOfOrderStatusItems[0].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[0].dividerColor = Color.Green
            listOfOrderStatusItems[1].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[1].dividerColor = Color.Green
            listOfOrderStatusItems[2].modifier = Modifier.alpha(1f)
            listOfOrderStatusItems[2].dividerColor = Color.Green
            listOfOrderStatusItems[3].modifier = Modifier.alpha(1f)
        }

        Constants.listOfOrderStatus.forEach {

        }
    }

    fun getOrders(){

        if (listOfOrders.isEmpty()) {

            viewModelScope.launch(Dispatchers.IO) {

                accountRepo.getOrders().collect {

                    when (it) {
                        is Resource.Error -> {

                            Log.e("error fetching orders", it.message.toString())

                            isLoading.value = false
                        }

                        is Resource.Loading -> {

                            isLoading.value = true
                        }

                        is Resource.Success -> {

                            it.data?.let { it1 -> listOfOrders.addAll(it1) }
                        }
                    }

                }
            }
        }
    }

    fun getOrderAddress(context: Context){

        viewModelScope.launch (Dispatchers.IO){


            accountRepo.getAddresses().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error getting addresses",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {

                        it.data?.forEach { addressItem ->

                            if (addressItem.label == User.appUser?.selectedAddress){


                                selectedOrder.value?.latitude = addressItem.latitude

                                selectedOrder.value?.longitude = addressItem.longitude

                                getReadableLocation(context )
                            }

                        }
                    }
                }
            }
        }
    }

    fun getReadableLocation(context:Context){

        val geocoder = Geocoder(context)
        val geocoderAddress = geocoder.getFromLocation(selectedOrder.value?.latitude?.toDouble()?:0.0,selectedOrder.value?.longitude?.toDouble()?:0.0,1)


        readableAddress = geocoderAddress?.get(0)?.getAddressLine(0) ?: ""
    }

    fun getListOfProductsInSelectedOrder(){



        listOfProductsInSelectedOrder = selectedOrder.value?.listOfOrderItems?.values as MutableCollection<ArrayList<String>>?

    }

}