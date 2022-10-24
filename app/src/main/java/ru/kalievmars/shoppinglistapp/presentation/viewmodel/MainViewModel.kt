package ru.kalievmars.shoppinglistapp.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.usecase.DeleteShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.EditShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.GetShopListUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val getShopListUseCase: GetShopListUseCase,
) : ViewModel(), LifecycleObserver {

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