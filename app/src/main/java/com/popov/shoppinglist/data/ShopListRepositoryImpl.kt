package com.popov.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.popov.shoppinglist.data.base.AppDataBase
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.repository.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()


    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }
}