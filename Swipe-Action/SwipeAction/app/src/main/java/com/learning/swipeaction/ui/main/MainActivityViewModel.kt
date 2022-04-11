package com.learning.swipeaction.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.learning.swipeaction.data.model.Student
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    private val _list: MutableState<List<Student>> = mutableStateOf(emptyList())
    val studentList get() = _list

    init {
        val list = mutableListOf<Student>()
        for (i in 0..50) {
            val student = Student(i, "Item", Random(System.nanoTime().toInt()).nextInt(15, 60))
            list.add(student)
        }
        _list.value = list
    }

    fun deleteItem(student: Student) {
        _list.value = _list.value.toMutableList().also {
            it.remove(student)
        }
    }
}