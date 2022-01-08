package com.learning.expandedlist

import androidx.lifecycle.ViewModel
import com.learning.expandedlist.data.ParentModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _parentList = MutableStateFlow<List<ParentModel>>(emptyList())
    val parentList: StateFlow<List<ParentModel>> get() = _parentList

    private val _expandedCardList = MutableStateFlow(listOf<Int>())
    val expandedCardList: StateFlow<List<Int>> get() = _expandedCardList

    init {
        val list = listOf(
            ParentModel(
                name = "Ahmed",
                image = R.drawable.img_1,
                children = listOf("Ahmed", "Ahmed")
            ),
            ParentModel(
                name = "Ahmed",
                image = R.drawable.img_2,
                children = listOf("Ahmed")
            ),
            ParentModel(
                name = "Ahmed",
                image = R.drawable.img_3,
                children = listOf("Ahmed", "Ahmed")
            ),
            ParentModel(
                name = "Adel",
                image = R.drawable.img_4,
            )
        )
        _parentList.value = list
    }

    fun cardArrowClick(cardId: Int) {
        _expandedCardList.value = _expandedCardList.value.toMutableList().also { list ->
            if (list.contains(cardId)) {
                list.remove(cardId)
            } else {
                list.add(cardId)
            }
        }
    }
}