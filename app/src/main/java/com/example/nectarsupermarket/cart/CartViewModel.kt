package com.example.nectarsupermarket.cart

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CheckoutSessionPost
import com.example.domain.model.Order
import com.example.domain.model.CartITem
import com.example.domain.contract.Resource
import com.example.domain.contract.User
import com.example.domain.contract.repository.CartRepo
import com.example.domain.contract.repository.ShopRepo
import com.google.firebase.Timestamp
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepo: CartRepo,
    private val shopRepo: ShopRepo
):ViewModel() {

    val isProductListLoading = mutableStateOf(true)

    val isLoadingGeneral = mutableStateOf(false)

    var cartList = mutableStateListOf<CartITem?>()

    val selectedProduct = mutableStateOf<CartITem?>(null)

    val cartTotalAmount = mutableStateOf<Double>(0.0)

    var orderPath : String = ""

    var orderTime:Timestamp? = null

    var customerConfig = mutableStateOf<PaymentSheet.CustomerConfiguration?>(null)

    var paymentIntentClientSecret = mutableStateOf<String?>(null)

    val isFetchingStripeDataComplete = mutableStateOf<Boolean>(false)

    val openFailedPaymentDialog = mutableStateOf<Boolean>(false)

    val isPaymentSuccessful = mutableStateOf<Boolean?>(null)


    fun getCartList(){

        viewModelScope.launch (Dispatchers.IO){

           cartRepo.getCartList().collect{

                when(it){

                    is Resource.Error -> {
                        Log.e("error getting cart list",it.message.toString())

                        isProductListLoading.value = false
                    }
                    is Resource.Loading -> {

                        isProductListLoading.value = true
                    }
                    is Resource.Success -> {



                        it.data?.let { it1 -> cartList.addAll(it1) }

                       getProductsInCartList()
                    }
                }
            }
        }
    }

    fun getProductsInCartList(){

        if (!cartList.isEmpty()){


            cartList.onEachIndexed {index,cartItem->

                viewModelScope.launch(Dispatchers.IO) {

                    if (cartItem != null) {

                       cartRepo.getProductsInCartList(cartItem).collect{

                            when(it){
                                is Resource.Error -> {
                                    Log.e("error getting products in cart",it.message.toString())

                                    isProductListLoading.value = false
                                }
                                is Resource.Loading -> {
                                    isProductListLoading.value = true
                                }
                                is Resource.Success -> {

                                    val product = it.data

                                    cartList[index]?.product = product

                                    if (cartList.size -1 == index){

                                        calculateCartTotal()

                                        isProductListLoading.value = false

                                    }


                                }
                            }

                        }
                    }
                }

            }




        }
        isProductListLoading.value = false
    }


    fun increaseProductQuantity(index:Int){

        if (selectedProduct.value?.quantitySelected!! >= selectedProduct.value?.product?.quantity!!){

            return

        } else {

            val selectedQuantity = selectedProduct.value!!.quantitySelected?.plus(1)

            val selectedTotalPrice = selectedProduct.value!!.product?.price?.times(selectedQuantity!!)

            selectedProduct.value = selectedProduct.value!!.copy(
                quantitySelected = selectedQuantity,
                totalPrice = selectedTotalPrice)

            cartList[index] = cartList[index]!!.copy(
                quantitySelected = selectedProduct.value!!.quantitySelected,
                totalPrice = selectedProduct.value!!.totalPrice)


            viewModelScope.launch(Dispatchers.IO) {

                shopRepo.addProductToCart(selectedProduct.value!!).collect{

                    when(it){
                        is Resource.Error -> {

                            Log.e("Error increasing product quantity",it.message.toString())
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {


                        }
                    }
                }

                calculateCartTotal()
            }
        }


    }

    fun decreaseProductQuantity(index: Int){

        if (selectedProduct.value?.quantitySelected!! ==1) {

            cartList.remove(selectedProduct.value)


            viewModelScope.launch (Dispatchers.IO){


               shopRepo.deleteProductFromCart(selectedProduct.value!!.product!!).collect {

                   when (it) {
                       is Resource.Error -> {

                           Log.e("Error decreasing product quantity", it.message.toString())
                       }

                       is Resource.Loading -> {

                       }

                       is Resource.Success -> {


                       }
                   }
               }
            }

        }else{

            val selectedQuantity = selectedProduct.value!!.quantitySelected?.minus(1)

            val selectedTotalPrice = selectedProduct.value!!.product?.price?.times(selectedQuantity!!)

            selectedProduct.value = selectedProduct.value!!.copy(
                quantitySelected = selectedQuantity,
                totalPrice = selectedTotalPrice)

            cartList[index] = cartList[index]!!.copy(
                quantitySelected = selectedProduct.value!!.quantitySelected,
                totalPrice = selectedProduct.value!!.totalPrice)

            viewModelScope.launch(Dispatchers.IO) {

                shopRepo.addProductToCart(selectedProduct.value!!).collect {

                    when (it) {
                        is Resource.Error -> {

                            Log.e("Error deleting product from", it.message.toString())
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {


                        }
                    }
                }

                calculateCartTotal()
            }


        }

    }

    fun deleteProductFromCart(){

        viewModelScope.launch (Dispatchers.IO){

            cartList.remove(selectedProduct.value)

            shopRepo.deleteProductFromCart(selectedProduct.value!!.product!!).collect {

                when (it) {
                    is Resource.Error -> {

                        Log.e("Error deleting product from", it.message.toString())
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {


                    }
                }
            }

            calculateCartTotal()

        }

    }

    fun deleteAllProductsFromCart() {


        viewModelScope.launch(Dispatchers.IO) {


            cartList.forEach {

                shopRepo.deleteProductFromCart(it?.product!!).collect{

                    when(it){
                        is Resource.Error -> {

                            Log.e("Error Deleting Product from cart",it.message.toString())
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {


                        }
                    }
                }

            }

            isPaymentSuccessful.value = true

            cartList.clear()


        }
    }


    fun calculateCartTotal(){

        cartTotalAmount.value = 0.0

        cartList.forEach { cartITem ->

            cartTotalAmount.value += cartITem?.totalPrice!!

        }
    }

    fun sendProductToStripeForPayment(){

        viewModelScope.launch (Dispatchers.IO){

            val cartTotalAmountInInt:Int = cartTotalAmount.value.toInt() * 100

            if (cartTotalAmount.value!=0.0)

            cartRepo.postPaymentDetailsToFirebase(CheckoutSessionPost(amount =cartTotalAmountInInt.toString() )).collect{


                when(it){
                    is Resource.Error -> {

                        Log.e("error posting cart to stripe",it.message.toString())

                        isLoadingGeneral.value = false
                    }
                    is Resource.Loading -> {

                        isLoadingGeneral.value = true
                    }
                    is Resource.Success -> {

                         orderPath= it.data.toString()

                        delay(3000)

                        getStripePaymentDetails()
                    }
                }
            }

        }
    }

    fun getStripePaymentDetails(){

        viewModelScope.launch(Dispatchers.IO) {

            cartRepo.getPaymentDetailsFromFirebaseStripe(orderPath).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error getting stripe payment info",it.message.toString())

                        isLoadingGeneral.value = false
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {

                        customerConfig.value =
                            it.data?.customer?.let { it1 -> it.data?.ephemeralKeySecret?.let { it2 ->
                                PaymentSheet.CustomerConfiguration(it1,
                                    it2
                                )
                            } }

                        paymentIntentClientSecret.value = it.data?.paymentIntentClientSecret

                        orderTime = it.data?.created

                        if (customerConfig.value == null || paymentIntentClientSecret.value == null){

                            getStripePaymentDetails()

                        }else{
                            isLoadingGeneral.value = false

                            isFetchingStripeDataComplete.value = true
                        }





                    }
                }
            }
        }
    }

    fun addNewOrder(){

        val listOfOrderHashMap = HashMap<String,Any>()

        val orderNumber = orderPath.substringAfterLast("/")

        cartList.forEach {

            listOfOrderHashMap.put(
                it?.product?.name.toString(),
                listOf(it?.product?.image?:"",it?.product?.name?:"",it?.product?.unit?:"",it?.product?.price.toString(),it?.quantitySelected.toString())
            )
        }

        viewModelScope.launch (Dispatchers.IO){

           cartRepo.addNewOrder(
                Order(orderNumber,1,cartTotalAmount.value,orderTime,
                User.appUser?.latitude,User.appUser?.longitude,User.appUser?.selectedAddressPath,listOfOrderHashMap)
            ).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error adding new order",it.message.toString())

                        isLoadingGeneral.value = false
                    }
                    is Resource.Loading -> {

                        isLoadingGeneral.value = true
                    }
                    is Resource.Success -> {

                        deleteAllProductsFromCart()
                    }
                }
            }
        }



    }

    fun presentPaymentSheet(
        paymentSheet: PaymentSheet,
        customerConfig: PaymentSheet.CustomerConfiguration,
        paymentIntentClientSecret: String
    ) {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Nectar",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true
            )
        )
    }

     fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {

                isPaymentSuccessful.value = false
            }
            is PaymentSheetResult.Failed -> {

                isPaymentSuccessful.value = false
            }
            is PaymentSheetResult.Completed -> {
                addNewOrder()
            }
        }
    }


}