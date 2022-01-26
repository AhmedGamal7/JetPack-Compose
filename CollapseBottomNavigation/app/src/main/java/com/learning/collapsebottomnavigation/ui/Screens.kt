package com.learning.collapsebottomnavigation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun MainProfileCompose(navController: NavHostController) {
}

@Composable
fun MainSearchCompose() {

}

@Composable
fun MainFavoriteCompose(navController: NavHostController) {

}

@Composable
fun MainHomeCompose(navController: NavHostController) {
    val list = mutableListOf<String>()
    repeat(100) {
        list.add("Item : $it")
    }
    LazyColumn {
        items(list) { item ->
            Text(text = item, fontSize = 30.sp, color = Color.Green)
        }
    }
}