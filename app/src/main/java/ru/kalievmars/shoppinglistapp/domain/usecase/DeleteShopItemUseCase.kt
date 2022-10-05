package ru.kalievmars.shoppinglistapp.domain.usecase

import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun execute(shopItem: ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }

}