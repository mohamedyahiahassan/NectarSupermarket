package com.example.data.di

import com.example.data.FirebaseDataSource.FirebaseShopRepositoryImpl
import com.example.data.repository.AccountRepoImpl
import com.example.data.repository.AuthenticationRepoImpl
import com.example.data.repository.CartRepoImpl
import com.example.data.repository.FavouriteRepoImpl
import com.example.data.repository.HomeRepoImpl
import com.example.data.repository.ProductRepoImpl
import com.example.data.repository.ShopRepoImpl
import com.example.domain.contract.FirebaseDataSource.FirebaseShopRepository
import com.example.domain.contract.repository.AccountRepo
import com.example.domain.contract.repository.AuthenticationRepo
import com.example.domain.contract.repository.CartRepo
import com.example.domain.contract.repository.FavouriteRepo
import com.example.domain.contract.repository.HomeRepo
import com.example.domain.contract.repository.ProductRepo
import com.example.domain.contract.repository.ShopRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideShopRepoImpl(impl: ShopRepoImpl): ShopRepo {

        return impl
    }

    @Provides
    fun provideAuthRepoImpl(impl: AuthenticationRepoImpl): AuthenticationRepo {

        return impl
    }

    @Provides
    fun provideProductRepoImpl(impl: ProductRepoImpl): ProductRepo {

        return impl
    }

    @Provides
    fun provideHomeRepoImpl(impl: HomeRepoImpl): HomeRepo {

        return impl
    }

    @Provides
    fun provideFavouriteRepoImpl(impl: FavouriteRepoImpl): FavouriteRepo {

        return impl
    }

    @Provides
    fun provideAccountRepoImpl(impl: AccountRepoImpl): AccountRepo {

        return impl
    }

    @Provides
    fun provideCartRepoImpl(impl: CartRepoImpl): CartRepo {

        return impl
    }
}
