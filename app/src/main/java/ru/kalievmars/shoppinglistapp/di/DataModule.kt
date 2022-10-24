package ru.kalievmars.shoppinglistapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.kalievmars.shoppinglistapp.data.AppDatabase
import ru.kalievmars.shoppinglistapp.data.dao.ShopListDao
import ru.kalievmars.shoppinglistapp.data.repository.ShopListRepositoryImpl
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).getShopListDao()
        }

    }

}