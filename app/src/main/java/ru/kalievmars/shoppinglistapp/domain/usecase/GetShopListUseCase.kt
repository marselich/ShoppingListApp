package ru.kalievmars.shoppinglistapp.domain.usecase

import androidx.lifecycle.LiveData
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.repository.ShopListRepository
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    fun execute(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }

}