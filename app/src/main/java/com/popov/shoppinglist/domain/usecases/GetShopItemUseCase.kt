package com.popov.shoppinglist.domain.usecases

import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(shopItemId: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}