package ru.kalievmars.shoppinglistapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.MainViewModel
import ru.kalievmars.shoppinglistapp.presentation.viewmodel.ShopItemViewModel

@Module
interface ShopViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel

}