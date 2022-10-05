package ru.kalievmars.shoppinglistapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.kalievmars.shoppinglistapp.data.AppDatabase
import ru.kalievmars.shoppinglistapp.data.ShopListMapper
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    val shopListDao = AppDatabase.getInstance(application).getShopListDao()
    private val mapper = ShopListMapper()


    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.insertShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val shopItem = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(shopItem)
    }
    //MediatorLiveData позволяет перехватывать события из другой LiveData и каким то образом реагировать на них
//    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>()
//        .apply {
//        addSource(shopListDao.getShopList()) {
////            if(it.size > 10)
//            value = mapper.mapListDbModelToListEntity(it)
//        }
//    }


    // если нужно только преобразовывать один тип в другой используется Transformations
    // под его капотом используется так же MediatorLiveData
    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }

}
