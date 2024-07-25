package com.example.data.di

import com.example.data.FirebaseDataSource.FirebaseAccountRepoImpl
import com.example.data.FirebaseDataSource.FirebaseAuthenticationRepoImpl
import com.example.data.FirebaseDataSource.FirebaseCartRepositoryImpl
import com.example.data.FirebaseDataSource.FirebaseFavouriteRepoImpl
import com.example.data.FirebaseDataSource.FirebaseHomeRepoImpl
import com.example.data.FirebaseDataSource.FirebaseShopRepositoryImpl
import com.example.domain.contract.FirebaseDataSource.FirebaseAccountRepository
import com.example.domain.contract.FirebaseDataSource.FirebaseAuthenticationRepository
import com.example.domain.contract.FirebaseDataSource.FirebaseCartRepository
import com.example.domain.contract.FirebaseDataSource.FirebaseFavouriteRepository
import com.example.domain.contract.FirebaseDataSource.FirebaseHomeRepository
import com.example.domain.contract.FirebaseDataSource.FirebaseShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseRepositoryModule {

    @Provides
    fun provideRepositoryImpl(impl: FirebaseAuthenticationRepoImpl):FirebaseAuthenticationRepository{

        return impl
    }

    @Provides
    fun provideShopRepositoryImpl(impl: FirebaseShopRepositoryImpl):FirebaseShopRepository{

        return impl
    }

    @Provides
    fun provideCartRepositoryImpl (impl:FirebaseCartRepositoryImpl):FirebaseCartRepository{

        return impl
    }

    @Provides
    fun provideFavouriteRepositoryImpl (impl:FirebaseFavouriteRepoImpl):FirebaseFavouriteRepository{

        return impl
    }

    @Provides
    fun provideHomeRepositoryImpl (impl: FirebaseHomeRepoImpl): FirebaseHomeRepository {

        return impl
    }

    @Provides
    fun provideAccountRepositoryImpl (impl: FirebaseAccountRepoImpl): FirebaseAccountRepository {

        return impl
    }
}