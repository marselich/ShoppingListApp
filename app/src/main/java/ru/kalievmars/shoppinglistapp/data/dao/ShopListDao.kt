package ru.kalievmars.shoppinglistapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kalievmars.shoppinglistapp.data.ShopItemDbModel
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_list")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_list WHERE id == :shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE) // is also Update with onConflict
    suspend fun insertShopItem(ShopItemDbModel: ShopItemDbModel)

//    @Delete
//    fun deleteShopItem(ShopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_list WHERE id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)


}