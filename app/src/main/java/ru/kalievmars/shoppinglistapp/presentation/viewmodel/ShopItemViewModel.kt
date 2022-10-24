package ru.kalievmars.shoppinglistapp.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kalievmars.shoppinglistapp.domain.models.ShopItem
import ru.kalievmars.shoppinglistapp.domain.usecase.AddShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.EditShopItemUseCase
import ru.kalievmars.shoppinglistapp.domain.usecase.GetShopItemUseCase
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase: GetShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
) : ViewModel(), LifecycleObserver {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemLiveData = MutableLiveData<ShopItem>()
    val shopItemLiveData: LiveData<ShopItem>
        get() = _shopItemLiveData

    private val _activityClosed = MutableLiveData<Unit>()
    val activityClosed: LiveData<Unit>
        get() = _activityClosed


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            _shopItemLiveData.value = getShopItemUseCase.execute(shopItemId)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        val validate = validateInput(name, count)

        if (validate) {
            _shopItemLiveData.value?.let {

                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        count = count
                    )
                    editShopItemUseCase.execute(item)
                    finishWork()
                }
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        val validate = validateInput(name, count)

        if (validate) {
            viewModelScope.launch {
                val shopItem = ShopItem(
                    name = name,
                    count = count,
                    enabled = true
                )
                addShopItemUseCase.execute(shopItem)
                finishWork()
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
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _activityClosed.value = Unit
    }


}