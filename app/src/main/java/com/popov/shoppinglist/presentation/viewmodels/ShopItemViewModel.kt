package com.popov.shoppinglist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.popov.shoppinglist.data.ShopListRepositoryImpl
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.usecases.AddShopItemUseCase
import com.popov.shoppinglist.domain.usecases.EditShopItemUseCase
import com.popov.shoppinglist.domain.usecases.GetShopItemUseCase

class ShopItemViewModel: ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if (fieldValid){
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)

        }
    }

    fun editShopItem(inputName: String?, inputCount: String?){ //TODO: not correct edit
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if (fieldValid){
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }

    }
    private fun parseName(inputName: String?): String{ //TODO: Show error input count
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int{ //TODO: Show error input count
        return try {
            inputCount?.trim()?.toInt() ?: 0
        }catch (e: Exception){
            0
        }
    }
    private fun validateInput(name: String, count: Int): Boolean {
        return name.isBlank() && count > 0
    }
}