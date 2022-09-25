package ru.kalievmars.shoppinglistapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_list")
    fun getShopList(): LiveData<List<ShopItem>>

    @Query("SELECT * FROM shop_list WHERE id == :shopItemId")
    fun getShopItem(shopItemId: Int): ShopItem

    @Insert
    fun insertShopItem(shopItem: ShopItem)

    @Delete
    fun deleteShopItem(shopItem: ShopItem)

    @Update
    fun updateShopItem(shopItem: ShopItem)

}