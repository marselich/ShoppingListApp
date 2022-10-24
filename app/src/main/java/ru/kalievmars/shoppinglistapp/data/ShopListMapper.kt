package ru.kalievmars.shoppinglistapp.data

import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>): List<ShopItem> = list.map {
        mapDbModelToEntity(it)
    }

}