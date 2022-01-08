package com.learning.expandedlist.data

data class ParentModel(
    val name: String = "Ahmed",
    val image: Int = 1,
    val children: List<String> = emptyList()
)
