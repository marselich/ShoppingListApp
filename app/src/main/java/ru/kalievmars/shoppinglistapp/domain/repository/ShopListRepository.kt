package ru.kalievmars.shoppinglistapp.domain.repository

import androidx.lifecycle.LiveData
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

}