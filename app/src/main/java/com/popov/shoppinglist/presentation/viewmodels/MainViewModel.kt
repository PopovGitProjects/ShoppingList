package com.popov.shoppinglist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.popov.shoppinglist.data.ShopListRepositoryImpl
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.usecases.DeleteShopItemUseCase
import com.popov.shoppinglist.domain.usecases.EditShopItemUseCase
import com.popov.shoppinglist.domain.usecases.GetShopListUseCase

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    val shopList = shopListLiveData as LiveData<List<ShopItem>>

    fun getShopList() {
        shopListLiveData.value = getShopListUseCase.getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}