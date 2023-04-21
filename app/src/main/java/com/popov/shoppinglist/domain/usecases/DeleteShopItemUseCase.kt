package com.popov.shoppinglist.domain.usecases

import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}