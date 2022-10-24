package ru.kalievmars.shoppinglistapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ru.kalievmars.shoppinglistapp.data.AppDatabase
import ru.kalievmars.shoppinglistapp.data.ShopListMapper
import ru.kalievmars.shoppinglistapp.data.dao.ShopListDao
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val shopItem = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(shopItem)
    }
    //MediatorLiveData позволяет перехватывать события из другой LiveData и каким то образом реагировать на них
    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>()
        .apply {
        addSource(shopListDao.getShopList()) {
//            if(it.size > 10)
            value = mapper.mapListDbModelToListEntity(it)
        }
    }


    // если нужно только преобразовывать один тип в другой используется Transformations
    // под его капотом используется так же MediatorLiveData
//    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
//        shopListDao.getShopList()
//    ) {
//        mapper.mapListDbModelToListEntity(it)
//    }

}
