package com.popov.shoppinglist.domain.usecases

import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopItem(shopItem)
    }
}