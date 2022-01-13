package com.learning.multiselectlistitem

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _myList = MutableStateFlow<List<String>>(emptyList())
    val myList: StateFlow<List<String>> get() = _myList

    private val _selectedItem = MutableStateFlow(listOf<Int>())
    val selectedItem: StateFlow<List<Int>> get() = _selectedItem


    init {
        val list = mutableListOf<String>()
        for (i in 0 until 50) {
            list.add("item : $i ")
        }
        _myList.value = list
    }


    fun listItemLongClick(cardId: Int) {
        _selectedItem.value = _selectedItem.value.toMutableList().also { list ->
            if (list.contains(cardId)) {
                list.remove(cardId)
            } else {
                list.add(cardId)
            }
        }
    }
}