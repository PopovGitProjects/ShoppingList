package com.popov.shoppinglist.domain.repository

import com.popov.shoppinglist.domain.models.ShopItem

interface ShopListRepository {
    fun getShopList(): List<ShopItem>
    fun getShopItem(shopItemId: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun editShopItemUseCase(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)

}