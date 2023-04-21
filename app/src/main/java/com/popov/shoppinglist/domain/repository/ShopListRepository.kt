package com.popov.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.popov.shoppinglist.domain.models.ShopItem

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun getShopItem(shopItemId: Int): ShopItem
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
}