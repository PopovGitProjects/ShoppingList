package com.popov.shoppinglist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.popov.shoppinglist.data.ShopListRepositoryImpl
import com.popov.shoppinglist.domain.models.ShopItem
import com.popov.shoppinglist.domain.usecases.AddShopItemUseCase
import com.popov.shoppinglist.domain.usecases.EditShopItemUseCase
import com.popov.shoppinglist.domain.usecases.GetShopItemUseCase

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputName = _errorInputName as LiveData<Boolean>
    val errorInputCount = _errorInputCount as LiveData<Boolean>

    private val _shopItemLiveData = MutableLiveData<ShopItem>()
    val shopItemLiveData = _shopItemLiveData as LiveData<ShopItem>

    private val _needToCloseScreen = MutableLiveData<Unit>()
    val needToCloseScreen = _needToCloseScreen as LiveData<Unit>

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItemLiveData.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            setNeedToCloseScreen()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) { //TODO: not correct edit
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            _shopItemLiveData.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                setNeedToCloseScreen()
            }
        }

    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true
        }
        if (count <= 0){
            result = false
            _errorInputCount.value = true
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false // TODO: Ошибка не уходит, разобраться!
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false // TODO: Ошибка не уходит, разобраться!
    }

    private fun setNeedToCloseScreen() {
        _needToCloseScreen.value = Unit
    }
}