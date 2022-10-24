package ru.kalievmars.shoppinglistapp.domain.usecase

import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun execute(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }

}

