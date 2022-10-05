package ru.kalievmars.shoppinglistapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kalievmars.shoppinglistapp.data.repository.ShopListRepositoryImpl
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository
import ru.kalievmars.shoppinglistapp.domain.usecase.DeleteShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.EditShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.GetShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.GetShopListUseCase

class MainViewModel(
    application: Application
) : ViewModel(), LifecycleObserver {

//    private val shopListRepositoryImpl: ShopListRepository =
//        ShopListRepositoryImpl(context = app.applicationContext)

    private val shopListRepositoryImpl = ShopListRepositoryImpl(application)

    private val deleteShopItemUseCase =
        DeleteShopItemUseCase(shopListRepository = shopListRepositoryImpl)
    private val editShopItemUseCase =
        EditShopItemUseCase(shopListRepository = shopListRepositoryImpl)
    private val getShopListUseCase =
        GetShopListUseCase(shopListRepository = shopListRepositoryImpl)


    val shopList = getShopListUseCase.execute()


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.execute(shopItem)
        }
    }

    fun changeEnableShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.execute(shopItem.copy(enabled = !shopItem.enabled))
        }
    }

}