package com.popov.shoppinglist.domain.usecases

import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}