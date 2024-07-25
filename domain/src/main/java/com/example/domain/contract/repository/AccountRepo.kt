package com.example.domain.contract.repository

import com.example.domain.contract.Resource
import com.example.domain.model.AddressItem
import com.example.domain.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface AccountRepo {


    suspend fun getOrders(): Flow<Resource<List<Order>>>

    suspend fun getAddresses(): Flow<Resource<List<AddressItem>>>

    suspend fun addNewAddress(address: AddressItem): Flow<Resource<Any>>
}