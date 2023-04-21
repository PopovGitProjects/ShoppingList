package com.popov.shoppinglist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.popov.shoppinglist.data.ShopListRepositoryImpl
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.usecases.DeleteShopItemUseCase
import com.popov.shoppinglist.domain.usecases.EditShopItemUseCase
import com.popov.shoppinglist.domain.usecases.GetShopListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopListLiveData = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            when (shopItem.enabled) {
                true -> {
                    editShopItemUseCase.editShopItem(shopItem.copy(enabled = false))
                }

                false -> {
                    editShopItemUseCase.editShopItem(shopItem.copy(enabled = true))
                }
            }
        }
    }
}