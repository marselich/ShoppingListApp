package ru.kalievmars.shoppinglistapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    //    private val shopListDatabase: ShopListDatabase by lazy {
//        ShopListDatabase.getInstance(context)
//    }
//    private val shopListDao: ShopListDao by lazy {
//        shopListDatabase.getShopListDao()
//    }
//
//    init {
//        for (i in 0 until 2) {
//            val shopItem = ShopItem(
//                name = "Vasya",
//                count = 2,
//                enabled = true
//            )
//            shopListDao.insertShopItem(shopItem)
//        }
//    }
//
//    override fun addShopItem(shopItem: ShopItem) {
//        shopListDao.insertShopItem(shopItem)
//    }
//
//    override fun deleteShopItem(shopItem: ShopItem) {
//        shopListDao.deleteShopItem(shopItem)
//    }
//
//    override fun editShopItem(shopItem: ShopItem) {
//        shopListDao.updateShopItem(shopItem)
//    }
//
//    override fun getShopItem(shopItemId: Int): ShopItem {
//        return shopListDao.getShopItem(shopItemId)
//    }
//
//    override fun getShopList(): LiveData<List<ShopItem>> {
//        return shopListDao.getShopList()

    private var autoIncrementId = 0

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    init {
        for (i in 0 until 10) {
            val shopItem = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}
