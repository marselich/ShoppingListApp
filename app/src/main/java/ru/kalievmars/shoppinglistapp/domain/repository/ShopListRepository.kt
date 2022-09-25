package ru.kalievmars.shoppinglistapp.domain.repository

import androidx.lifecycle.LiveData
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

}