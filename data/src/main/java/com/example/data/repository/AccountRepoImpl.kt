package com.example.data.repository

import com.example.data.FirebaseDataSource.FirebaseAccountRepoImpl
import com.example.domain.contract.FirebaseDataSource.FirebaseAccountRepository
import com.example.domain.contract.Resource
import com.example.domain.contract.repository.AccountRepo
import com.example.domain.model.AddressItem
import com.example.domain.model.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepoImpl @Inject constructor(private val firebaseAccountRepository: FirebaseAccountRepository):AccountRepo {
    override suspend fun getOrders(): Flow<Resource<List<Order>>> {

        return firebaseAccountRepository.getOrders()
    }

    override suspend fun getAddresses(): Flow<Resource<List<AddressItem>>> {

        return firebaseAccountRepository.getAddresses()
    }

    override suspend fun addNewAddress(address: AddressItem): Flow<Resource<Any>> {

        return firebaseAccountRepository.addNewAddress(address)
    }

}