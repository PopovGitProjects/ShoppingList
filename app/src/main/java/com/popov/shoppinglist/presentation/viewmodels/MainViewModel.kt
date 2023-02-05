package com.popov.shoppinglist.presentation.viewmodels

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

    val shopListLiveData = getShopListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
//        val newItem = shopItem.copy(enabled = shopItem.enabled)
//        editShopItemUseCase.editShopItem(newItem)
        when(shopItem.enabled){
            true -> {editShopItemUseCase.editShopItem(shopItem.copy(enabled = false))}
            false -> {editShopItemUseCase.editShopItem(shopItem.copy(enabled = true))}
        }
    }
}