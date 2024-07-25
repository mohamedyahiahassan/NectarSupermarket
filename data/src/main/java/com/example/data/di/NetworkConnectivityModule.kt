package com.example.data.di

import com.example.data.networkConnectivity.ConnectivityObserverImpl
import com.example.domain.ConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectivityModule {


    @Provides
    fun provideNetworkConnectivity(impl: ConnectivityObserverImpl):ConnectivityObserver{

        return impl
    }


}