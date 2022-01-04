package com.learning.listexample

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    private var _list: MutableState<List<String>> = mutableStateOf(emptyList())
    val list: State<List<String>> get() = _list

    init {
        val myList: MutableList<String> = mutableListOf()
        for (i in 0 until 100) {
            myList.add("Item : $i")
        }
        _list.value = myList
    }
}