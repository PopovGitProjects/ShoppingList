package com.popov.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.popov.shoppinglist.domain.models.ShopItem

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    fun getShopItem(shopItemId: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}