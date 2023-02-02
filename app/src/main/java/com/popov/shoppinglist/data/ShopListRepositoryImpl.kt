package com.popov.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLiveDate = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ p0, p1 ->            //сортировка листа по id
        p0.id.compareTo(p1.id)
    })
    private var autoIncrementId = 0

    init {
        for (i in 0..10) {
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveDate
    }

    override fun getShopItem(shopItemId: Int): ShopItem {

        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element with id: $shopItemId not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    private fun updateList() {
        shopListLiveDate.value = shopList.toList()
    }
}